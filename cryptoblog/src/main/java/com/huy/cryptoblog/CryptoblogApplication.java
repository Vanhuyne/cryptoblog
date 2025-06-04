package com.huy.cryptoblog;

import com.huy.cryptoblog.model.Category;
import com.huy.cryptoblog.model.Post;
import com.huy.cryptoblog.repository.CategoryRepo;
import com.huy.cryptoblog.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@RequiredArgsConstructor()
		// This annotation enables Spring Data's support for web applications, allowing for easy pagination and sortin
public class CryptoblogApplication {
	private final CategoryRepo categoryRepo;
	private final PostRepo postRepo;

	public static void main(String[] args) {
		SpringApplication.run(CryptoblogApplication.class, args);

	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
//			Category category1 = categoryRepo.findByName("Tech").orElseGet(() -> categoryRepo.save(new Category(null, "Tech", new ArrayList<>())));
//			List<Post> posts = new ArrayList<>();
//			for (int i = 1; i <= 12; i++) {
//				String title = "Post " + i;
//				String slug = "post-" + i;
//
//				Post post = Post.builder()
//						.title(title)
//						.slug(slug)
//						.content("This is the content for " + title)
//						.summary("This is the summary for " + title)
//						.coverImageUrl("https://example.com/image" + i + ".jpg")
//						.isPublished(i % 2 == 0) // alternate published status
//						.category(category1)
//						.createdAt(LocalDateTime.now().minusDays(12 - i))
//						.updatedAt(LocalDateTime.now().minusDays(12 - i))
//						.build();
//
//				posts.add(post);
//			};
//
//			postRepo.saveAll(posts);
//
		};

	}

}
