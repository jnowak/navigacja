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

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.android.maps.GeoPoint;

public class MapWebServices
{
	
	
	public static String getDistanceAndTime(String origin, String destination, TransportMode transportMode) 
	{
		try
		{
			URI uri = buildURI(Service.distance, origin, destination, transportMode);
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
	
	
	public static Step[] getRoute(String origin, String destination, TransportMode transportMode)
	{
		URI uri;
		try
		{
			uri = buildURI(Service.directions, origin, destination, transportMode);
			URLConnection connection = uri.toURL().openConnection();
			connection.connect();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(connection.getInputStream());
			NodeList steppp = doc.getElementsByTagName("step");
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath path = xPathFactory.newXPath();
			XPathExpression stepXPath = path.compile("//DirectionsResponse/route/leg/step");
			XPathExpression startXPath = path.compile("//DirectionsResponse/route/leg/step/start_location/");
			XPathExpression endXPath = path.compile("//DirectionsResponse/route/leg/step/end_location");
			XPathExpression durationXPath = path.compile("//DirectionsResponse/route/leg/step/duration/text");
			XPathExpression distanceXPath = path.compile("//DirectionsResponse/route/leg/step/distance/text");
			XPathExpression instructionsXPath = path.compile("//DirectionsResponse/route/leg/step/html_instructions");
			
			NodeList stepsList = (NodeList) stepXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList startList = (NodeList) startXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList endList = (NodeList) endXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList durationList = (NodeList) durationXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList distanceList = (NodeList) distanceXPath.evaluate(doc, XPathConstants.NODESET);
			NodeList instructionsList = (NodeList) instructionsXPath.evaluate(doc, XPathConstants.NODESET);
			
			Step[] steps = new Step[stepsList.getLength()];
			
			for (int i = 0; i < stepsList.getLength(); i++)
			{
				String s = stepsList.item(i).getChildNodes().item(0).getNodeName();
				String name = startList.item(i).getNodeName();
				String fc = startList.item(i).getNodeValue();
				double startLat = Double.valueOf(startList.item(i).getFirstChild().getNodeValue());
				double startLng = Double.valueOf(startList.item(i).getLastChild().getNodeValue());
				GeoPoint start = new GeoPoint((int)(startLat*1E6), (int)(startLng*1E6));
				
				double endLat = Double.valueOf(endList.item(i).getFirstChild().getNodeValue());
				double endLng = Double.valueOf(endList.item(i).getLastChild().getNodeValue());
				GeoPoint end = new GeoPoint((int)(endLat*1E6), (int)(endLng*1E6));
				
				String duration = durationList.item(i).getNodeValue();
				String distance = distanceList.item(i).getNodeValue();
				String instructions = instructionsList.item(i).getNodeValue();
				steps[i] = new Step(start, end, duration, distance, instructions);
			}
			
			
		
			return null;

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
	
	private static URI buildURI(Service service, String origin, String destination, TransportMode transportMode) throws URISyntaxException
	{
	
		String originParam = (service == Service.distance) ? "origins" : "origin";
		String destinationParam = (service == Service.distance) ? "destinations" : "destination";
		String url = String.format("http://maps.googleapis.com/maps/api/%s/xml?language=pl" +
				"&mode=%s" +
				"&sensor=false" +
				"&%s=%s" +
				"&%s=%s", 
				new Object[] {
				service,
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
