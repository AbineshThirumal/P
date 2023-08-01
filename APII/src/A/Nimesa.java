package A;


	import java.io.IOException;
	import java.io.InputStream;
	import java.net.URL;
	import java.util.Scanner;

	import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Nimesa 
{
	    private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

	    public static void main(String[] args) throws JSONException 
	    {
	        Scanner scanner = new Scanner(System.in);

	        try {
	            JSONObject data = getWeatherDataFromAPI();

	            int option;
	            do {
	                printMenu();
	                option = scanner.nextInt();

	                switch (option) {
	                    case 1:
	                        getAndPrintTemperature(data);
	                        break;
	                    case 2:
	                        getAndPrintWindSpeed(data);
	                        break;
	                    case 3:
	                        getAndPrintPressure(data);
	                        break;
	                    case 0:
	                        System.out.println("Exiting...");
	                        break;
	                    default:
	                        System.out.println("Invalid option. Please try again.");
	                }
	            } while (option != 0);
	        } catch (IOException e) {
	            System.out.println("Error fetching data from API: " + e.getMessage());
	        } finally {
	            scanner.close();
	        }
	    }

	    private static JSONObject getWeatherDataFromAPI() throws IOException, JSONException {
	        URL url = new URL(API_URL);
	        InputStream inputStream = url.openStream();
	        Scanner scanner = new Scanner(inputStream);
	        String jsonString = scanner.useDelimiter("\\A").next();
	        scanner.close();
	        return new JSONObject(jsonString);
	    }

	    private static void printMenu() {
	        System.out.println("\nOptions:");
	        System.out.println("1. Get weather");
	        System.out.println("2. Get Wind Speed");
	        System.out.println("3. Get Pressure");
	        System.out.println("0. Exit");
	        System.out.print("Enter your choice: ");
	    }

	    private static void getAndPrintTemperature(JSONObject data) throws JSONException {
	        String date = getDateFromUser();
	        printWeatherData(data, date, "temp", "Temperature", "°C");
	    }

	    private static void getAndPrintWindSpeed(JSONObject data) throws JSONException {
	        String date = getDateFromUser();
	        printWeatherData(data, date, "wind", "Wind Speed", "m/s");
	    }

	    private static void getAndPrintPressure(JSONObject data) throws JSONException {
	        String date = getDateFromUser();
	        printWeatherData(data, date, "main", "Pressure", "hPa");
	    }

	    private static String getDateFromUser() {
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter the date (yyyy-MM-dd): ");
	        return scanner.next();
	    }

	    private static void printWeatherData(JSONObject data, String date, String key, String dataName, String unit) throws JSONException {
	        JSONArray list = data.getJSONArray("list");
	        boolean found = false;
	        for (int i = 0; i < list.length(); i++) {
	            JSONObject item = list.getJSONObject(i);
	            String dtTxt = item.getString("dt_txt").split(" ")[0];
	            if (dtTxt.equals(date)) {
	                JSONObject main = item.getJSONObject(key);
	                double value = main.getDouble(dataName.toLowerCase());
	                System.out.println(dataName + " on " + dtTxt + ": " + value + " " + unit);
	                found = true;
	                break;
	            }
	        }

	        if (!found) {
	            System.out.println("No " + dataName.toLowerCase() + " data available for the specified date.");
	        }
	    }
	}


	
	


