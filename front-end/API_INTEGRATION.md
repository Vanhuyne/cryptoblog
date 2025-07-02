# Angular Post Service Integration

This Angular service integrates with your Spring Boot backend API to manage blog posts and categories.

## Setup

The service is configured to work with your Spring Boot backend running on `http://localhost:8080`. Make sure your backend is running before using the frontend.

## Files Created

### Models
- `src/app/model/post.model.ts` - TypeScript interfaces for Post-related data
- `src/app/model/category.model.ts` - TypeScript interfaces for Category-related data
- `src/app/model/index.ts` - Barrel export for models

### Services
- `src/app/services/post.service.ts` - Service for Post API operations
- `src/app/services/category.service.ts` - Service for Category API operations
- `src/app/services/index.ts` - Barrel export for services

### Environment Configuration
- `src/environments/environment.ts` - Development environment config
- `src/environments/environment.prod.ts` - Production environment config

### Example Component
- `src/app/components/posts.component.ts` - Example component showing how to use the service

## Usage Examples

### Injecting the Service

```typescript
import { Component, OnInit } from '@angular/core';
import { PostService } from '../services/post.service';
import { PostResponse } from '../model/post.model';

@Component({
  selector: 'app-my-component',
  template: '...'
})
export class MyComponent implements OnInit {
  constructor(private postService: PostService) { }
}
```

### Getting All Posts

```typescript
ngOnInit(): void {
  this.postService.getAllPosts().subscribe({
    next: (posts) => {
      console.log('Posts:', posts);
      this.posts = posts;
    },
    error: (error) => {
      console.error('Error:', error);
    }
  });
}
```

### Creating a New Post

```typescript
createPost(): void {
  const newPost: CreatePostRequest = {
    title: 'My New Post',
    content: 'This is the content of my post...',
    summary: 'A brief summary',
    isPublished: true,
    categoryId: 'category-uuid-here'
  };

  this.postService.createPost(newPost).subscribe({
    next: (createdPost) => {
      console.log('Post created:', createdPost);
    },
    error: (error) => {
      console.error('Error creating post:', error);
    }
  });
}
```

### Getting a Single Post

```typescript
getPost(id: string): void {
  this.postService.getPostById(id).subscribe({
    next: (post) => {
      console.log('Post:', post);
    },
    error: (error) => {
      console.error('Error:', error);
    }
  });
}
```

### Updating a Post

```typescript
updatePost(id: string): void {
  const updates: UpdatePostRequest = {
    title: 'Updated Title',
    isPublished: true
  };

  this.postService.updatePost(id, updates).subscribe({
    next: (updatedPost) => {
      console.log('Post updated:', updatedPost);
    },
    error: (error) => {
      console.error('Error updating post:', error);
    }
  });
}
```

### Deleting a Post

```typescript
deletePost(id: string): void {
  this.postService.deletePost(id).subscribe({
    next: () => {
      console.log('Post deleted successfully');
    },
    error: (error) => {
      console.error('Error deleting post:', error);
    }
  });
}
```

## Available API Methods

### PostService

- `getAllPosts()` - Get all posts
- `getPostById(id: string)` - Get a post by ID
- `getPostBySlug(slug: string)` - Get a post by slug
- `getPostsByCategory(categoryId: string)` - Get posts by category
- `getPublishedPosts()` - Get only published posts
- `createPost(post: CreatePostRequest)` - Create a new post
- `updatePost(id: string, post: UpdatePostRequest)` - Update a post
- `deletePost(id: string)` - Delete a post
- `publishPost(id: string)` - Publish a post
- `unpublishPost(id: string)` - Unpublish a post
- `searchPosts(query: string)` - Search posts

### CategoryService

- `getAllCategories()` - Get all categories
- `getCategoryById(id: string)` - Get a category by ID
- `getCategoryBySlug(slug: string)` - Get a category by slug
- `createCategory(category: CreateCategoryRequest)` - Create a new category
- `updateCategory(id: string, category: UpdateCategoryRequest)` - Update a category
- `deleteCategory(id: string)` - Delete a category

## Error Handling

The services include comprehensive error handling that:
- Catches HTTP errors
- Provides meaningful error messages
- Logs errors to the console
- Returns user-friendly error messages

## CORS Configuration

Make sure your Spring Boot backend has CORS properly configured. The backend should include:

```java
@CrossOrigin(origins = "http://localhost:4200")
```

Or configure CORS globally in your Spring Boot application.

## Running the Application

1. Make sure your Spring Boot backend is running on `http://localhost:8080`
2. Start the Angular development server: `npm start`
3. The frontend will be available at `http://localhost:4200`

## Notes

- UUIDs are represented as strings in TypeScript
- Dates are represented as ISO string format
- The service uses RxJS Observables for async operations
- All HTTP operations include proper error handling
- The service is provided at root level, so it's a singleton across the application
