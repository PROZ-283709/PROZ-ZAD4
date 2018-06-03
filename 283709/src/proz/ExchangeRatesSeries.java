package proz;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ExchangeRatesSeries")
public class ExchangeRatesSeries 
{
	@XmlElement(name = "Table")
	private String Table;
	@XmlElement(name = "Currency")
	private String Currency;
	@XmlElement(name = "Code")
	private String Code;
	
	@XmlElementWrapper(name="Rates")
	@XmlElement(name = "Rate")
	private List<Rate> Rate = new ArrayList<Rate>();
		
	public double getAvgSum()
	{
		double sum = 0.0;
		
		for(Rate r : Rate)
			sum += Double.parseDouble(r.getAsk());
			
		return (double)(Math.round((sum / Rate.size())*10000))/10000;
	}
	
	public List<Rate> getRate()
	{
		return Rate;
	}

	public void setRate(List<Rate> rate)
	{
		Rate = rate;
	}
		
	public String getTable() 
	{
		return Table;
	}

	public void setTable(String table) 
	{
		Table = table;
	}

	public String getCurrency() 
	{
		return Currency;
	}


	public void setCurrency(String currency) 
	{
		Currency = currency;
	}


	public String getCode()
	{
		return Code;
	}


	public void setCode(String code)
	{
		
		Code = code;
	}
}