package metadatarepo.core.metadataexport;

import com.mongodb.*;
import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleMetaVersion;
import util.mongodb.MongoDBDataSource;
import util.mongodb.exception.DuplicateDocumentException;
import util.mongodb.exception.NoSuchDocumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
class MongoDBBuildDependenciesRepository implements BuildDependenciesRepository {

    public static final String BUILD_DEPENDENCIES_SECTION = "buildDependencies";
    private static final String MODULE_COLLECTION = "modules";
    private final MongoDBDataSource mongoDBDataSource;

    public MongoDBBuildDependenciesRepository(MongoDBDataSource mongoDBDataSource) {
        this.mongoDBDataSource = mongoDBDataSource;
    }

    @Override
    public List<BuildDependency> getDependencies(ModuleId moduleId) {

        DBCollection modulesCollection = mongoDBDataSource.getMongoDB().getCollection(MODULE_COLLECTION);
        DBCursor dbCursor = null;

        try {

            dbCursor = modulesCollection.find(getQueryDBObject(moduleId));
            if (!dbCursor.hasNext()) {
                throw new NoSuchDocumentException();
            }

            DBObject dbObject = dbCursor.next();
            if (dbCursor.hasNext()) {
                throw new DuplicateDocumentException();
            }

            return extractAndGetDependenciesFromDBObject(dbObject);

        } finally {
            if (dbCursor != null) {
                dbCursor.close();
            }
        }
    }

    private BasicDBObject getQueryDBObject(ModuleId moduleId) {
        ModuleMetaVersion metaVersion = moduleId.getMetaVersion();
        if (metaVersion.isStatusDefined()) {
            return new BasicDBObject("org", moduleId.getOrg())
                    .append("name", moduleId.getName())
                    .append("version", metaVersion.getVersion().getValue())
                    .append("status", moduleId.getMetaVersion().getStatus().getValue());
        } else {
            return new BasicDBObject("org", moduleId.getOrg())
                    .append("name", moduleId.getName())
                    .append("version", metaVersion.getVersion().getValue());
        }
    }

    private List<BuildDependency> extractAndGetDependenciesFromDBObject(DBObject dbObject) {
        Object buildDependencies = dbObject.get(BUILD_DEPENDENCIES_SECTION);
        if (!(buildDependencies instanceof BasicDBList)) {
            throw new RuntimeException("You must have a build dependencies section.");
        }
        BasicDBList dependenciesList = (BasicDBList) buildDependencies;

        List<BuildDependency> dependenciesResult = new ArrayList<BuildDependency>();
        for (Object dependencyObject : dependenciesList) {
            BasicDBObject dependencyDBObject = (BasicDBObject) dependencyObject;
            dependenciesResult.add(buildBuildDependency(dependencyDBObject));
        }

        return dependenciesResult;
    }

    private BuildDependency buildBuildDependency(BasicDBObject dependencyDBObject) {
        Map dependencyMap = dependencyDBObject.toMap();
        String org = extractRequiredField(dependencyMap, "org", String.class);
        String name = extractRequiredField(dependencyMap, "name", String.class);
        String version = extractOptionalField(dependencyMap, "metadatarepo/core/version", String.class);
        return new BuildDependency(org, name, version, dependencyMap);
    }

    private <T> T extractRequiredField(Map dependencyMap, String fieldName, Class<T> clazz) {
        return extractAndRemoveField(true, dependencyMap, fieldName, clazz);
    }

    private <T> T extractOptionalField(Map dependencyMap, String fieldName, Class<T> clazz) {
        return extractAndRemoveField(false, dependencyMap, fieldName, clazz);
    }

    private <T> T extractAndRemoveField(boolean mandatoryField, Map dependencyMap, String fieldName, Class<T> clazz) {
        Object value = dependencyMap.get(fieldName);

        if (mandatoryField && value == null) {
            throw new RuntimeException();
        }

        if (!clazz.isAssignableFrom(value.getClass())) {
            throw new RuntimeException();
        }

        dependencyMap.remove(fieldName);
        return clazz.cast(value);
    }
}
