package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @RequestMapping("/")
    public String index(Model model){
//        Book book = new Book();
//        book.setTitle("Harry Potter");
//
//        Author author = new Author();
//        author.setName("J.K. Rowling");
//
//        Set<Author> authors = new HashSet<Author>();
//        authors.add(author);
//        Set<Book> books = new HashSet<Book>();
//        books.add(book);
//        author.setBooks(books);
//        authorRepository.save(author);
//        book.setAuthors(authors);
//        bookRepository.save(book);
//
       model.addAttribute("books", bookRepository.findAll());
       model.addAttribute("authors", authorRepository.findAll());
        return "index";
    }

    @GetMapping("/addbook")
    public String addbook(Model model){
        model.addAttribute("book", new Book());
        return "add";
    }

    @PostMapping("/processbook")
    public String addb(@ModelAttribute Book book){
        bookRepository.save(book);
        return "redirect:/";
    }

    @GetMapping("/addauthor")
    public String adddirector(Model model){
        model.addAttribute("author", new Author());
        model.addAttribute("books", bookRepository.findAll());
        return "adda";
    }

    @PostMapping("/processauthor")
    public String adddir(@ModelAttribute Author author, @RequestParam("authorId") long id){
        Book book = bookRepository.findById(id).get();
       // Iterable<Book> books = bookRepository.findAll();
        Set<Book> booksReal;
        if(author.books != null){
            booksReal = new HashSet<>(author.books);
        }
        else{
            booksReal = new HashSet<>();
        }
        booksReal.add(book);
        author.setBooks(booksReal);
        authorRepository.save(author);
        return "redirect:/";
    }
}
