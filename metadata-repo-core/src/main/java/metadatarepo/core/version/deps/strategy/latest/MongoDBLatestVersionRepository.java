package metadatarepo.core.version.deps.strategy.latest;

import com.mongodb.*;
import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleIdFactory;
import util.mongodb.MongoDBDataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class MongoDBLatestVersionRepository implements LatestVersionRepository {

    private static final String MONGODB_ARTIFACTS_LATEST_COLLECTION = "module.latest";
    private final MongoDBDataSource mongoDBDataSource;

    public MongoDBLatestVersionRepository(MongoDBDataSource mongoDBDataSource) {
        this.mongoDBDataSource = mongoDBDataSource;
    }

    @Override
    public String getLatestVersionValue(String org,
                                        String name,
                                        String status) {

        if (org == null) {
            throw new IllegalStateException("A requested organisation is required.");
        }

        if (name == null) {
            throw new IllegalStateException("A requested name is required.");
        }

        if (status == null) {
            throw new IllegalStateException("A requested status is required.");
        }

        String latestVersion = null;
        DBCursor cursor = null;
        try {
            DB mongoDB = mongoDBDataSource.getMongoDB();

            DBCollection coll = mongoDB.getCollection(MONGODB_ARTIFACTS_LATEST_COLLECTION);
            BasicDBObject idDoc =
                    new BasicDBObject("org", org)
                            .append("name", name)
                            .append("type", "binary")
                            .append("status", status);

            BasicDBObject query = new BasicDBObject("_id", idDoc);
            cursor = coll.find(query);

            while (cursor.hasNext()) {
                BasicDBObject doc = (BasicDBObject) cursor.next();
                latestVersion = doc.getString("value");
            }

            DBObject err = mongoDB.getLastError();
            if (!((CommandResult) err).ok()) {
                throw ((CommandResult) err).getException();
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return latestVersion;
    }

    @Override
    public Map<String, Collection<ModuleId>> getLatestDependencies() {
        Map<String, Collection<ModuleId>> latestDependencies = new HashMap<String, Collection<ModuleId>>();
        DBCursor cursor = null;
        try {
            DB mongoDB = mongoDBDataSource.getMongoDB();
            DBCollection coll = mongoDB.getCollection(MONGODB_ARTIFACTS_LATEST_COLLECTION);
            cursor = coll.find();
            while (cursor.hasNext()) {
                BasicDBObject doc = (BasicDBObject) cursor.next();
                buildDependency(latestDependencies, doc);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return latestDependencies;
    }

    private void buildDependency(
            Map<String, Collection<ModuleId>> latestDependencies,
            BasicDBObject document) {

        BasicDBObject basicDBObjectId = (BasicDBObject) document.get("_id");
        String latestVersion = document.getString("value");
        final String status = basicDBObjectId.getString("status");

        ModuleId moduleId =
                ModuleIdFactory.get(
                        basicDBObjectId.getString("org"),
                        basicDBObjectId.getString("name"),
                        latestVersion,
                        status);

        addDependencyToMap(latestDependencies, status, moduleId);
    }

    private void addDependencyToMap(Map<String, Collection<ModuleId>> latestDependencies, String status, ModuleId moduleId) {
        Collection<ModuleId> dependencies = latestDependencies.get("status");
        if (dependencies == null) {
            dependencies = new ArrayList<ModuleId>();
        }
        dependencies.add(moduleId);
        latestDependencies.put(status, dependencies);
    }
}
