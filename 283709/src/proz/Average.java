package proz;

import java.io.StringReader;
import java.net.URI;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Path("/exchangerates/rates")
public class Average
{
	@GET
	@Path("{table}/{code}/{topCount}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTXT(@PathParam("table") String table, @PathParam("code") String code, @PathParam("topCount") String topCount)
	{
		return getAvg(table, code, topCount);
	}
	
	@GET
	@Path("{table}/{code}/{topCount}")
	@Produces(MediaType.TEXT_HTML)
	public String getHTML(@PathParam("table") String table, @PathParam("code") String code, @PathParam("topCount") String topCount)
	{
		return "<html><body><h1>" + getAvg(table, code, topCount) + "</h1></body></html>";
	}
	
	@GET
	@Path("{table}/{code}/{topCount}")
	@Produces(MediaType.TEXT_XML)
	public String getXML(@PathParam("table") String table, @PathParam("code") String code, @PathParam("topCount") String topCount)
	{
			return "<?xml version=\"1.0\"?>" + "<average rate>" + getAvg(table, code, topCount) + "</average rate>";
	}
	
	@GET
	@Path("{table}/{code}/{topCount}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJS(@PathParam("table") String table, @PathParam("code") String code, @PathParam("topCount") String topCount)
	{
			JsonObjectBuilder jsBuilder = Json.createObjectBuilder();
			jsBuilder.add("average rate", getAvg(table, code, topCount));
			JsonObject js = jsBuilder.build();
			return js.toString();
	}
	
	public static String getAvg(String table, String code, String topCount)
	{
		Client client = ClientBuilder.newClient();
		URI uri = UriBuilder.fromUri("http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + code + "/last/" + topCount).build();
		WebTarget webTarget = client.target(uri);
		
		String XMLData =  webTarget.request().accept(MediaType.TEXT_XML).get(String.class);
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(ExchangeRatesSeries.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(XMLData);
			ExchangeRatesSeries example = (ExchangeRatesSeries) unmarshaller.unmarshal(reader);
	        
			double sum = example.getAvgSum();
			return String.valueOf(sum);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
		
		return "0";      	        
	}
}