package plugins.maven.pomparent;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import util.mongodb.MongoDBDataSource;

/**
 * @author Gregory Boissinot
 */
class MongoDBBOMRepository implements BOMRepository {

    private static final String MONGODB_BOM_VERSION_COLLECTION = "bom.version.counter";

    private final MongoDBDataSource mongoDBDataSource;

    public MongoDBBOMRepository(MongoDBDataSource mongoDBDataSource) {
        this.mongoDBDataSource = mongoDBDataSource;
    }

    @Override
    public String getLatestBOMVersion() {
        DB mongoDB = mongoDBDataSource.getMongoDB();
        DBCollection coll = mongoDB.getCollection(MONGODB_BOM_VERSION_COLLECTION);
        DBObject dbObject = coll.findOne();
        return String.class.cast(dbObject.get("val"));
    }
}
