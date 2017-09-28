package client;

import java.util.ArrayList;
import java.util.List;

import domain.Book;
import domain.Chapter;
import domain.Publisher;
import service.BookStoreService;

public class BookStoreClient {

	public static void main(String[] args) {
		BookStoreService service = new BookStoreService();

		Publisher publisher = new Publisher("MANN", "Maning Publications");

		Book book = new Book("974876", "Java Persistence with Hibernate", publisher);

		List<Chapter> chapters = new ArrayList<Chapter>();
		Chapter chapter1 = new Chapter("Introduction", 1);
		Chapter chapter2 = new Chapter("Domain and Models", 2);
		chapters.add(chapter1);
		chapters.add(chapter2);

		book.setChapters(chapters);

		service.persistObjectGraph(book);

		Book retrievedBook = service.retrieveObjectGraph("974876");
		System.out.println(retrievedBook);

	}

}
