package com.core.service;

import com.core.dto.BorrowRequestDto;
import com.core.dto.ReturnRequestDto;
import com.core.exception.BorrowingLimitExceededException;
import com.core.exception.RecordNotFoundException;
import com.core.model.Book;
import com.core.model.Magazine;
import com.core.model.SubscriptionPlan;
import com.core.model.User;
import com.core.repository.BookRepository;
import com.core.repository.MagazineRepository;
import com.core.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final MagazineRepository magazineRepository;
    private final UserRepository userRepository;

    public LibraryService(BookRepository bookRepository, MagazineRepository magazineRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.magazineRepository = magazineRepository;
        this.userRepository = userRepository;
    }

    public String borrowItem(BorrowRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        if (user.getMonthlyTransactionCount() >= 10) {
            throw new BorrowingLimitExceededException("User has exceeded monthly transaction limit");
        }

        if (request.getType().equalsIgnoreCase("BOOK")) {
            Optional<Book> bookOpt = bookRepository.findByTitle(request.getTitle());
            if (bookOpt.isEmpty() || !bookOpt.get().isAvailable()) {
                throw new RecordNotFoundException("Book not available");
            }

            Book book = bookOpt.get();
            if (book.getGenre().equalsIgnoreCase("Crime") && user.getAge() < 18) {
                throw new BorrowingLimitExceededException("User is not allowed to borrow Crime genre books");
            }

            enforceBorrowingLimits(user, 1, 0);
            book.setAvailable(false);
            bookRepository.save(book);
        } else if (request.getType().equalsIgnoreCase("MAGAZINE")) {
            Optional<Magazine> magazineOpt = magazineRepository.findByTitle(request.getTitle());
            if (magazineOpt.isEmpty() || !magazineOpt.get().isAvailable()) {
                throw new RecordNotFoundException("Magazine not available");
            }

            Magazine magazine = magazineOpt.get();
            enforceBorrowingLimits(user, 0, 1);
            magazine.setAvailable(false);
            magazineRepository.save(magazine);
        } else {
            throw new IllegalArgumentException("Invalid type");
        }

        user.setMonthlyTransactionCount(user.getMonthlyTransactionCount() + 1);
        userRepository.save(user);
        return "Borrowing successful!";
    }

    public String returnItems(ReturnRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        for (String title : request.getTitles()) {
            Optional<Book> bookOpt = bookRepository.findByTitle(title);
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                book.setAvailable(true);
                bookRepository.save(book);
                user.setBorrowedBooksCount(user.getBorrowedBooksCount() - 1);
            } else {
                Optional<Magazine> magazineOpt = magazineRepository.findByTitle(title);
                if (magazineOpt.isPresent()) {
                    Magazine magazine = magazineOpt.get();
                    magazine.setAvailable(true);
                    magazineRepository.save(magazine);
                    user.setBorrowedMagazinesCount(user.getBorrowedMagazinesCount() - 1);
                } else {
                    throw new RecordNotFoundException("Item not found: " + title);
                }
            }
        }

        userRepository.save(user);
        return "Return successful!";
    }

    private void enforceBorrowingLimits(User user, int books, int magazines) {
        SubscriptionPlan plan = user.getSubscriptionPlan();
        int maxBooks = 0, maxMagazines = 0;

        switch (plan) {
            case SILVER -> maxBooks = 2;
            case GOLD -> {
                maxBooks = 3;
                maxMagazines = 1;
            }
            case PLATINUM -> {
                maxBooks = 4;
                maxMagazines = 2;
            }
        }

        if (user.getBorrowedBooksCount() + books > maxBooks || user.getBorrowedMagazinesCount() + magazines > maxMagazines) {
            throw new BorrowingLimitExceededException("Borrowing limit exceeded for subscription plan: " + plan);
        }

        user.setBorrowedBooksCount(user.getBorrowedBooksCount() + books);
        user.setBorrowedMagazinesCount(user.getBorrowedMagazinesCount() + magazines);
    }
}
