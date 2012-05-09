package mapy.cyfrowe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

import com.google.android.maps.GeoPoint;

public class MapWebServices
{
	
	
	public static String getDistanceAndTime(String origin, String destination, TransportMode transportMode) 
	{
		try
		{
			URI uri = buildURI("xml", Service.distance, origin, destination, transportMode);
			URLConnection connection = uri.toURL().openConnection();
			connection.connect();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(connection.getInputStream());
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath path = xPathFactory.newXPath();
			XPathExpression durationXPath = path.compile("//DistanceMatrixResponse/row/element/duration/text/text()");
			XPathExpression distanceXPath = path.compile("//DistanceMatrixResponse/row/element/distance/text/text()");
			
			String duration = (String) durationXPath.evaluate(doc, XPathConstants.STRING);
			String distance = (String) distanceXPath.evaluate(doc, XPathConstants.STRING);
			
			return String.format("Czas podróży: %s, Odległość: %s", new Object[] {duration, distance});
			
			
		} 
		catch (URISyntaxException e)
		{
			return null;
		} 
		catch (MalformedURLException e)
		{
			return null;
		} 
		catch (IOException e)
		{
			return null;
		} catch (ParserConfigurationException e)
		{
			return null;
		} catch (SAXException e)
		{
			return null;
		} catch (XPathExpressionException e)
		{
			return null; 
		}		
		
	}
	
	
	public static Step[] getRoute1(String origin, String destination, TransportMode transportMode)
	{
		URI uri;
		try
		{
			uri = buildURI("xml", Service.directions, origin, destination, transportMode);
			URLConnection connection = uri.toURL().openConnection();
			connection.connect();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(connection.getInputStream());
			NodeList steppp = doc.getElementsByTagName("step");
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath path = xPathFactory.newXPath();
			XPathExpression stepXPath = path.compile("//DirectionsResponse/route/leg/step");
			XPathExpression startLatXPath = path.compile("//DirectionsResponse/route/leg/step/start_location/lat/text()");
			XPathExpression startLngXPath = path.compile("//DirectionsResponse/route/leg/step/start_location/lng/text()");
			XPathExpression endLatXPath = path.compile("//DirectionsResponse/route/leg/step/end_location/lat/text()");
			XPathExpression endLngXPath = path.compile("//DirectionsResponse/route/leg/step/end_location/lng/text()");
			XPathExpression durationXPath = path.compile("//DirectionsResponse/route/leg/step/duration/text/text()");
			XPathExpression distanceXPath = path.compile("//DirectionsResponse/route/leg/step/distance/text/text()");
			XPathExpression instructionsXPath = path.compile("//DirectionsResponse/route/leg/step/html_instructions/text()");
			
			NodeList stepsList = (NodeList) stepXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList startLatList = (NodeList) startLatXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList startLngList = (NodeList) startLngXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList endLatList = (NodeList) endLatXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList endLngList = (NodeList) endLngXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList durationList = (NodeList) durationXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList distanceList = (NodeList) distanceXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList instructionsList = (NodeList) instructionsXPath.evaluate(doc, XPathConstants.NODESET);
			
			Step[] steps = new Step[stepsList.getLength()];
			
			for (int i = 0; i < stepsList.getLength(); i++)
			{
				double startLat = Double.valueOf(startLatList.item(i).getNodeValue());
				double startLng = Double.valueOf(startLngList.item(i).getNodeValue());
				GeoPoint start = new GeoPoint((int)(startLat*1E6), (int)(startLng*1E6));
				
				double endLat = Double.valueOf(endLatList.item(i).getNodeValue());
				double endLng = Double.valueOf(endLngList.item(i).getNodeValue());
				GeoPoint end = new GeoPoint((int)(endLat*1E6), (int)(endLng*1E6));
				
				String duration = durationList.item(i).getNodeValue();
				String distance = distanceList.item(i).getNodeValue();
				String instructions = instructionsList.item(i).getNodeValue();
				steps[i] = new Step(start, end, duration, distance, instructions);
			}
			
			
		
			return steps;

		} catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (XPathExpressionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		

	}
	
	
	public static Step[] getRoute(String origin, String destination, TransportMode transportMode)
	{
		URI uri;

			try
			{
				uri = buildURI("json", Service.directions, origin, destination, transportMode);
				URLConnection connection = uri.toURL().openConnection();
				connection.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				char[] buffer = new char[4096];
				StringBuilder output = new StringBuilder();
				
				String line;
				while ((line = reader.readLine()) != null)
				{
					output.append(line);
				}
				
				JSONObject json = new JSONObject(output.toString());
				
				JSONArray routes = json.getJSONArray("routes");
				JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
				JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");
				Step[] step = new Step[steps.length()];
				
				for (int i = 0; i < steps.length(); i++)
				{
					String distance = steps.getJSONObject(i).getJSONObject("distance").getString("text");
					String duration = steps.getJSONObject(i).getJSONObject("duration").getString("text");
					String instructions = steps.getJSONObject(i).getString("html_instructions");
					
					instructions = instructions.replaceAll("</?[a-z]+>", "");
					
					
					JSONObject startLocation = steps.getJSONObject(i).getJSONObject("start_location");
					Double startLat = Double.valueOf(startLocation.getString("lat"));
					Double startLng = Double.valueOf(startLocation.getString("lng"));
					GeoPoint start = new GeoPoint((int)(startLat*1E6), (int)(startLng*1E6));
					
					JSONObject endLocation = steps.getJSONObject(i).getJSONObject("end_location");
					Double endLat = Double.valueOf(endLocation.getString("lat"));
					Double endLng = Double.valueOf(endLocation.getString("lng"));
					GeoPoint end = new GeoPoint((int)(endLat*1E6), (int)(endLng*1E6));
					
					
					
					step[i] = new Step(start, end, duration, distance, instructions);
					
				}
				
				return step;
				
				
				
			} catch (URISyntaxException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}


	}
	
	private static URI buildURI(String format, Service service, String origin, String destination, TransportMode transportMode) throws URISyntaxException
	{
	
		String originParam = (service == Service.distance) ? "origins" : "origin";
		String destinationParam = (service == Service.distance) ? "destinations" : "destination";
		String url = String.format("http://maps.googleapis.com/maps/api/%s/%s?language=pl" +
				"&mode=%s" +
				"&sensor=false" +
				"&%s=%s" +
				"&%s=%s", 
				new Object[] {
				service,
				format,
				transportMode,
				originParam,
				origin,
				destinationParam,
				destination
				});
		URI uri = new URI(url);
		return uri;
		
	}
	
	private enum Service
	{
		distance ("distancematrix"),
		directions ("directions");
		private String name;
		private Service(String name)
		{
			this.name = name;
		}
		
		
		public String toString()
		{
			return name;
		}
	}

}
