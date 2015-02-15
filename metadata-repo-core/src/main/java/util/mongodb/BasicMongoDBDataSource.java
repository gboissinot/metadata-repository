package util.mongodb;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import util.mongodb.exception.MongoDBException;

import java.net.UnknownHostException;

/**
 * @author Gregory Boissinot
 */
public class BasicMongoDBDataSource implements MongoDBDataSource {

    private final MongoClient mongoClient;
    private final String mongoDBName;

    public BasicMongoDBDataSource(String mongoHost, int mongoPort, String mongoDBName) {
        try {
            mongoClient = new MongoClient(mongoHost, mongoPort);
        } catch (UnknownHostException ue) {
            throw new MongoDBException(ue);
        }
        this.mongoDBName = mongoDBName;
    }

    @Override
    public MongoClient getMongo() {
        return mongoClient;
    }

    @Override
    public DB getMongoDB() {
        return mongoClient.getDB(mongoDBName);
    }
}
