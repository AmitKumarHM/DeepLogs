package edu.nitdelhi.deeplogs.controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;

import edu.nitdelhi.deeplogs.User;
import edu.nitdelhi.deeplogs.bean.Covid19StatewiseTestingDetails;
import edu.nitdelhi.deeplogs.bean.SimpleLog;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;




@Controller
//@SessionAttributes("name")
public class HomeController {

	@RequestMapping(value="/index*", method = RequestMethod.GET)
	private String index() {
		return "loginpage";
	}

	@RequestMapping(value="/dashboard", method = RequestMethod.GET)
	private String dashboard() {
		return "dashboard";
	}

	@RequestMapping(value="/analysis", method = RequestMethod.GET)
	private String analysis() {
		return "analysis";
	}

	@RequestMapping(value="/collections", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private @ResponseBody Set<String> getCollection() {
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "deeplogs"));
		return mongoOps.getCollectionNames();
	}

	@RequestMapping(value="/documents/{collection}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private @ResponseBody List<SimpleLog> getDocument(@PathVariable("collection") String collectionName) {
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "deeplogs"));
		MongoCollection<Document> collection=mongoOps.getCollection(collectionName);
		MongoCursor<Document> itrD=collection.find().iterator();
		List<SimpleLog> listLog=new ArrayList<>();
		while(itrD.hasNext()) {
			Document d=itrD.next();
			Iterator<Object>  itr=d.values().iterator();
			SimpleLog sl=new SimpleLog();
			while(itr.hasNext()) {
				Object s=itr.next();
				if(s!=null) {
					sl.setMessageByLine(s.toString());
				}
			}
			listLog.add(sl);
		}
		return listLog;
	}

	@RequestMapping(value="/india", method = RequestMethod.GET)
	private String covid19India() {

		FileReader fr;
		BufferedReader bufr;
		try {
			fr = new FileReader("F:\\deeplogs\\uploads\\StatewiseTestingDetails.csv");
			bufr = new BufferedReader(fr); 
			String line = bufr.readLine();	
			//line = bufr.readLine();
			MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "covid19"));
			MongoCollection<Document> collection=mongoOps.getCollection("COVID19_STATEWISE_TESTING_DETAILS");
			while(line != null){  
				line = bufr.readLine();
				String[] fields=line.split(",");
				Document log = new Document();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
				
				int i=0;
				for(String token : fields) {
				String keyName=	Covid19StatewiseTestingDetails.class.getDeclaredFields()[i].getName();
				Class c=Covid19StatewiseTestingDetails.class.getDeclaredFields()[i].getType();
				if(c.getName().equals("java.lang.Integer"))
					if(!token.trim().equals("-") && !token.trim().isBlank() && !token.trim().isEmpty())
				        log.append(keyName, Integer.parseInt(token.trim()));
					else
						log.append(keyName, 0);
				else if(c.getName().equals("java.lang.Double"))
					if(!token.trim().equals("-") && !token.trim().isBlank() && !token.trim().isEmpty())
				        log.append(keyName, Double.parseDouble(token.trim()));
					else
						log.append(keyName, 0d);
				else if(c.getName().equals("java.util.Date")) {
					Date date=formatter.parse(token.trim());
					log.append(keyName,date);
				}
				/*
				 * else if(c.toString().equals("java.sql.Time")) log.append(keyName,
				 * Time.valueOf(token));
				 */
				else
					log.append(keyName, token);
				i++;
				}
				InsertOneResult result=collection.insertOne(log);
				System.out.println(result.wasAcknowledged());
			}
			bufr.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "chartist";
	}

	
	
	@RequestMapping(value="/chartlist", method = RequestMethod.GET)
	private String chartlist() {

		FileReader fr;
		BufferedReader bufr;
		try {
			fr = new FileReader("F:\\deeplogs\\uploads\\apache_logs1.txt");
			bufr = new BufferedReader(fr); 
			String line = bufr.readLine();	
			MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "deeplogs"));
			MongoCollection<Document> collection=mongoOps.getCollection("rawlog");
			while(line != null){  
				line = bufr.readLine();
				Document log = new Document("_id", new ObjectId());
				log.append("log", line);
				InsertOneResult result=collection.insertOne(log);
				System.out.println(result.wasAcknowledged());
			}
			bufr.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "chartist";
	}


	@RequestMapping(value="/tokenize", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private @ResponseBody List<SimpleLog> tokenize(@RequestParam("messageByLine") String text) {
		List<SimpleLog> responselist =new ArrayList<>();
		try {
			SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
			String[] tokens = tokenizer.tokenize(text);

			InputStream inputStreamNameFinder = getClass()
					.getResourceAsStream("/models/en-ner-time.bin");
			TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
			NameFinderME nameFinderME = new NameFinderME(model);
			List<Span> spans = Arrays.asList(nameFinderME.find(tokens));
			Iterator<Span> iitr=spans.iterator();
			while(iitr.hasNext()) {
				Span span=iitr.next();
				SimpleLog sl =new SimpleLog();
				int s=span.getStart();
				int e=span.getEnd();
				String message="";
				while(s!=e+1)
					message=message+tokens[s++];
				sl.setMessageByLine(message);
				sl.setProbability(span.getProb()*100);
				sl.setObjectId("time");
				responselist.add(sl);
			}

			//Date
			inputStreamNameFinder = getClass()
					.getResourceAsStream("/models/en-ner-date.bin");
			model = new TokenNameFinderModel(inputStreamNameFinder);
			nameFinderME = new NameFinderME(model);
			spans = Arrays.asList(nameFinderME.find(tokens));
			iitr=spans.iterator();
			while(iitr.hasNext()) {
				Span span=iitr.next();
				SimpleLog sl =new SimpleLog();
				int s=span.getStart();
				int e=span.getEnd();
				String message="";
				while(s!=e+1)
					message=message+tokens[s++];
				sl.setMessageByLine(message);
				sl.setProbability(span.getProb()*100);
				sl.setObjectId("date");
				responselist.add(sl);
			}


			//Person
			inputStreamNameFinder = getClass()
					.getResourceAsStream("/models/en-ner-person.bin");
			model = new TokenNameFinderModel(inputStreamNameFinder);
			nameFinderME = new NameFinderME(model);
			spans = Arrays.asList(nameFinderME.find(tokens));
			iitr=spans.iterator();
			while(iitr.hasNext()) {
				Span span=iitr.next();
				SimpleLog sl =new SimpleLog();
				int s=span.getStart();
				int e=span.getEnd();
				String message="";
				while(s!=e+1)
					message=message+tokens[s++];
				sl.setMessageByLine(message);
				sl.setProbability(span.getProb()*100);
				sl.setObjectId("person");
				responselist.add(sl);
			}

			//Percentage
			inputStreamNameFinder = getClass()
					.getResourceAsStream("/models/en-ner-percentage.bin");
			model = new TokenNameFinderModel(inputStreamNameFinder);
			nameFinderME = new NameFinderME(model);
			spans = Arrays.asList(nameFinderME.find(tokens));
			iitr=spans.iterator();
			while(iitr.hasNext()) {
				Span span=iitr.next();
				SimpleLog sl =new SimpleLog();
				int s=span.getStart();
				int e=span.getEnd();
				String message="";
				while(s!=e+1)
					message=message+tokens[s++];
				sl.setMessageByLine(message);
				sl.setProbability(span.getProb()*100);
				sl.setObjectId("percentage");
				responselist.add(sl);

			}

			//organization
			inputStreamNameFinder = getClass()
					.getResourceAsStream("/models/en-ner-organization.bin");
			model = new TokenNameFinderModel(inputStreamNameFinder);
			nameFinderME = new NameFinderME(model);
			spans = Arrays.asList(nameFinderME.find(tokens));
			iitr=spans.iterator();
			while(iitr.hasNext()) {
				Span span=iitr.next();
				SimpleLog sl =new SimpleLog();
				int s=span.getStart();
				int e=span.getEnd();
				String message="";
				while(s!=e+1)
					message=message+tokens[s++];
				sl.setMessageByLine(message);
				sl.setProbability(span.getProb()*100);
				sl.setObjectId("organization");
				responselist.add(sl);

			}
			
			//location
			inputStreamNameFinder = getClass()
					.getResourceAsStream("/models/en-ner-location.bin");
			model = new TokenNameFinderModel(inputStreamNameFinder);
			nameFinderME = new NameFinderME(model);
			spans = Arrays.asList(nameFinderME.find(tokens));
			iitr=spans.iterator();
			while(iitr.hasNext()) {
				Span span=iitr.next();
				SimpleLog sl =new SimpleLog();
				int s=span.getStart();
				int e=span.getEnd();
				String message="";
				while(s!=e+1)
					message=message+tokens[s++];
				sl.setMessageByLine(message);
				sl.setProbability(span.getProb()*100);
				sl.setObjectId("location");
				responselist.add(sl);

			}


		} catch (Exception e1) {
			e1.printStackTrace();
		}  
		return responselist;
	}


	@RequestMapping(value="/blank", method = RequestMethod.GET)
	private String blank() {
		return "blank-page";
	}


	@RequestMapping(value = "/login",method = RequestMethod.GET)
	private ModelAndView login(@RequestParam("email") String email,@RequestParam("password") String password,HttpServletResponse response) {
		System.out.println(email+"   "+password);
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "deeplogs"));
		User ub=mongoOps.findOne(new Query(where("email").is(email).and("password").is(password)), User.class);
		System.out.println(ub.getF_name());
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", ub);
		mav.setViewName("dashboard");
		return mav;
	}
}
