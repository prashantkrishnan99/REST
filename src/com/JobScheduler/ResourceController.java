package com.JobScheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.Job;
import bean.Profile;
import bean.Resource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

/**
Created by PrashantKrishnanIyer 
**/

@Path("/resource")
public class ResourceController {

	/** Class objects **/
	Resource r;
	Profile p;
	Job j;
	
	String result = "";
	
	/** JSON structure objects **/
	JSONArray jsonarray;
	JSONObject newjob; 
	JSONObject update;
	JSONObject report;
	
	/** Database components for MONGO **/
	Mongo mongo;
	DB db;
	DBCollection jobs,up,rep;
	DBObject dbObject;
	
	/** default constructor **/
	public ResourceController() {
	}
	
	/** Whenever a new job has to be created,
	 * this function is initiated **/
	@POST
	@Path("/NEWJOB")
	@Produces("application/json")
	@Consumes("application/json")
	public Response NEWJOB(@Context HttpServletRequest request) {
		try {
			Date date = new Date();
			mongo = new Mongo("localhost", 27017);
			db = mongo.getDB("DB");
			jobs = db.getCollection("listofjobs");
			
			DBCollection eventList = db.getCollection("events"); 			
			
			StringBuilder buffer = new StringBuilder();   
			BufferedReader stream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			while ((line = stream.readLine()) != null) {
			        buffer.append(line);
			}
			
			String data = buffer.toString();
			
			JSONObject obj = new JSONObject(data);
			JSONObject event1 = new JSONObject();
			
			dbObject = (DBObject) JSON.parse(data);			
			jobs.insert(dbObject);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			event1.put("eventTime",dateFormat.format(date));
			event1.put("type","NEWJOB");
			event1.put("jobstatus","NEW");
			event1.put("resourceid",obj.getJSONObject("job").getJSONObject("resource").getInt("_id"));
            event1.put("jobid", obj.getJSONObject("job").getInt("_id"));
            event1.put("jobtype", obj.getJSONObject("job").getJSONObject("resource").getString("job_type"));
			
            DBObject dbevent = (DBObject) JSON.parse(event1.toString());         
            eventList.insert(dbevent);
			
		    } catch(Exception e ) {
			       e.printStackTrace();
			       return Response.status(415).entity("{\"response\" : \"Unsuccessful Insertion\"}").build();
		      }
			       return Response.status(200).entity("{\"response\" : \"Successfully Inserted\"}").build();
	}
	
	/** This function is called to update the system;
	 * takes the job id as a parameter **/	 
	@POST
	@Path("/UPDATE")
	@Produces("application/json")
	@Consumes("application/json")
	public Response UPDATE(@Context HttpServletRequest request) throws IOException {
		try {
			Date date = new Date();
			mongo = new Mongo("localhost", 27017);
			db = mongo.getDB("DB");
			up = db.getCollection("listofjobs");
			
			DBCollection event = db.getCollection("events");
			
			StringBuilder buffer = new StringBuilder();
			BufferedReader stream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			while ((line = stream.readLine()) != null) {
			        buffer.append(line);
			}
			
			String data = buffer.toString();
			JSONObject obj = new JSONObject(data);
			
			DBObject selection = (DBObject) JSON.parse(data);			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
			
			
			BasicDBObject query = new BasicDBObject("job", new BasicDBObject("_id", obj.getJSONObject("job").getInt("_id")));
			DBCursor result = up.find(query);			
			JSONObject jsonresult = new JSONObject(result.next());
					
			obj.getJSONObject("job").put("finish",dateFormat.format(date));
			obj.getJSONObject("job").put("status","COMPLETE");
			obj.getJSONObject("job").put("resource","");
			String update = obj.toString();
			dbObject = (DBObject) JSON.parse(update);	
						
			up.update(selection,dbObject);
			
			JSONObject event1 = new JSONObject();
			dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			event1.put("eventTime",dateFormat.format(date));
			event1.put("type","UPDATE");
			event1.put("jobstatus","COMPLETE");
			event1.put("resourceid",jsonresult.getJSONObject("job").getJSONObject("resource").getInt("_id"));
            event1.put("jobid", obj.getJSONObject("job").getInt("_id"));
            event1.put("jobtype", "");
			
            DBObject dbevent = (DBObject) JSON.parse(event1.toString());         
            event.insert(dbevent);
            
			DBCursor u = up.find();
			while (u.hasNext()) {
				System.out.println(u.next());
			}
			System.out.println("Done");
		    }   catch (UnknownHostException e) {
			           e.printStackTrace();
			           return Response.status(415).entity("{\"response\" : \"Unsuccessful Updation\"}").build();
		        } catch (MongoException e) {
			             e.printStackTrace();
		          }
		    return Response.status(200).entity("{\"response\" : \"Successfully Updated\"}").build();
	}
	
	/** This function is called to view all the updates 
	 * done to the system;
	 * It displays the latest updated JSON structure **/
	@POST
	@Path("/REPORT")
	@Produces("application/json")
	@Consumes("application/json")	
	public Response REPORT(@Context HttpServletRequest request){
		try {
			JSONObject jsonobj;
			String jobtitle,name;
			
			JSONArray result = new JSONArray();
			JSONObject temp;
			mongo = new Mongo("localhost", 27017);
			db = mongo.getDB("DB");
			rep = db.getCollection("listofjobs");
			
			DBCursor r = rep.find();
			while (r.hasNext()) 
			{				
				jsonobj = new JSONObject(r.next());
				System.out.println(jsonobj.toString());
				jobtitle = jsonobj.getJSONObject("job").getJSONObject("resource").getJSONObject("resourceprofile").getString("job_type");
				name = jsonobj.getJSONObject("job").getJSONObject("resource").getString("name");
			    temp = new JSONObject();
			    temp.put("JOB_TYPE", jobtitle);
			    temp.put("EMP_NAME", name);
			    result.put(temp);
			}       
		    }   catch (UnknownHostException e) {
			           e.printStackTrace();
			           return Response.status(415).entity("{\"response\" : \"UnSuccessful Report\"}").build();
		        } catch (MongoException e) {
			             e.printStackTrace();
		          }
		   return Response.status(200).entity(result.toString()).build();
	}
}
