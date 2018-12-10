package com.example.demo.controller;
import com.example.demo.config.JwtTokenUtil;
import com.example.demo.json.JsonWeather;
import com.example.demo.model.WeatherDto;
import com.example.demo.model.BookCategory;
import com.example.demo.model.User;
import com.example.demo.repository.BookCategoryRepository;
import com.example.demo.repository.WeatherRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import com.example.demo.service.WeatherService;
import com.example.demo.weatherAdds.WeatherDayPeriodEnum;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.example.demo.json.GlobalWeatherObject;

import static com.example.demo.model.Constants.TOKEN_PREFIX;

@RestController
@RequestMapping("test")
public class Controller
{
	@Autowired
	BookRepository bookRepository;
	@Autowired
	BookService bookService;
	@Autowired
	BookCategoryRepository bookCategoryRepository;
	@Autowired
	WeatherService weatherService;
	@Autowired
	UserService userService;
	@Autowired
	JwtTokenUtil javaTokenUtil;
	@Autowired
    WeatherRepository weatherRepository;

    JsonRestTemplate jsonRestTemplate = new JsonRestTemplate();

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Book getBook(@PathVariable Long id)
	{
		return bookRepository.findOne(id);
	}
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Book> getList() { return bookRepository.findAll(); }
	@RequestMapping(value="", method = RequestMethod.GET)
	public Book findByName(@RequestParam(value = "name", required = false) String name) { return bookRepository.findByName(name);}
	@RequestMapping(value="/start", method = RequestMethod.GET)
	public Book findByNameStartingWith(@RequestParam(value = "arg", required = false) String name) { return bookRepository.findByNameStartingWith(name);}
	@RequestMapping(value="/end", method = RequestMethod.GET)
	public Book findByNameEndingWith(@RequestParam(value = "arg", required = false) String name) { return bookRepository.findByNameEndingWith(name);}
	///////////////////////////////////
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public Book addRecord (@RequestBody Book book) {return bookRepository.save(book);}
	@RequestMapping(value="/edit", method = RequestMethod.PUT)
	public Book editRecord (@RequestBody Book book) {return bookRepository.save(book);}
	@RequestMapping(value="/patch", method = RequestMethod.PATCH)
	public void editRecordData (@RequestBody Book book) {bookService.update(book);}
	@RequestMapping(value ="/delete", method = RequestMethod.DELETE)
	public void removeRecord (@RequestBody Book book) {bookRepository.delete(book.getId());}
	////////////////////////////////////
	@RequestMapping("/cats")
	public List<BookCategory> getAll() {return bookCategoryRepository.findAll();}
	@RequestMapping(value="/cats/books", method = RequestMethod.GET)
	public List<Book> findByBookCategory(@RequestParam(value = "id", required = false) Long id) {
	    BookCategory tmp = new BookCategory();
	    tmp.setId(id);
	    return bookRepository.findByBookCategory(tmp);
	}
	@RequestMapping(value="/post", method = RequestMethod.POST)
	public Book postBook(@RequestBody Book book) throws IOException {
        //ResponseEntity<?> restResponse = jsonRestTemplate.post("http://localhost:8080/test/add", book);
        return jsonRestTemplate.postForObject("http://localhost:8080/test/add" ,book, Book.class);
        //return restResponse.getBody().toString();
	}
    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public Book putBook(@RequestBody Book book) throws IOException{
	    return jsonRestTemplate.putForObject("http://localhost:8080/test/edit" , book, Book.class);
    }
    @RequestMapping(value = "/book/patch", method = RequestMethod.PATCH)
    public void patchBook(@RequestBody Book book) throws IOException{
        ResponseEntity<?> restResponse = jsonRestTemplate.patch("http://localhost:8080/test/patch", book);
        //return "Patch Succesfull or not?";
    }
//	public List<Book> listBooks(){
//		List<Book> tmp = new ArrayList<Book>();
//		for (BookCategory b : bookCategoryRepository.findAll()){
//			tmp.add(bookRepository.findByBookCategory(b));
//		}
//	}
//	public List<Book> listBooks() {
//		List<Book> tmp = new ArrayList<Book>();
//		for (BookCategory b : bookCategoryRepository.findAll()) {
//			tmp.add(getBookForListBooks(b.getId()));
//		}
//		return tmp;
//	}
//	public Book getBookForListBooks(Long id) {return bookRepository.findOne(id);}
	@RequestMapping(value="/show", method = RequestMethod.GET)
	public JsonWeather showWeather (@RequestHeader(value = "Authorization",required = false) String token) throws IOException, JSONException
	{
        token = token.replace(TOKEN_PREFIX,"");
		String userName=javaTokenUtil.getUsernameFromToken(token);
		System.out.println(userName);
		//return weatherRepository.findByUserName(userName);
		User user = userService.findOne(userName);
		Date date = new Date();

		JsonWeather list = jsonRestTemplate.getForObject("http://api.openweathermap.org/data/2.5/forecast?id="+user.getCityId()+"&APPID=be942a017ff05b9cb1075118347221f2", JsonWeather.class);
		WeatherDto weather = new WeatherDto();

        weather.setPressure(list.getList().get(0).getMain().getPressure().floatValue());
        weather.setTemp((list.getList().get(0).getMain().getTemp().floatValue() - 273.15f));
        weather.setHumidity(list.getList().get(0).getMain().getHumidity());
        weather.setLocation_id(user.getCityId());
        weather.setDate(date);
        WeatherDayPeriodEnum tmp = WeatherDayPeriodEnum.getWeatherDayPeriodEnumByDate(date);
        weather.setWeatherDayPeriodEnum(tmp);
        //weather.setWeather_id();
		WeatherDto fromDB = weatherRepository.findOne(user.getCityId());
		if (fromDB != null) {
			if (fromDB.getWeatherDayPeriodEnum().getTimeFrom() < weather.getWeatherDayPeriodEnum().getTimeFrom() || fromDB.getDate().compareTo(weather.getDate()) < 0) {
				//update
				weatherService.update(weather);
			}
		}else{
			weatherRepository.save(weather);
		}
		return list;
	}

}
