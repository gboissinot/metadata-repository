package util.mongodb;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * @author Gregory Boissinot
 */
public interface MongoDBDataSource {

    public MongoClient getMongo();

    public DB getMongoDB();
}
