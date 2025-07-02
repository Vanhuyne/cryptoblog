import { Component, OnInit } from '@angular/core';
import { PostService } from '../services/post.service';
import { PostResponse, PaginatedPostResponse, PageInfo } from '../model/post.model';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  standalone: false
})
export class PostsComponent implements OnInit {
  posts: PostResponse[] = [];
  pageInfo: PageInfo | null = null;
  currentPage = 0;
  pageSize = 5;
  loading = false;
  error: string | null = null;

  // Make Math available in template
  Math = Math;

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(page: number = 0): void {
   
    this.loading = true;
    this.error = null;
    this.currentPage = page;
    
    console.log('Loading posts for page:', page);
    console.log('Making API request...');
    
    this.postService.getAllPosts(page, this.pageSize).subscribe({
      next: (response) => {
        console.log('SUCCESS: Service returned response:', response);
        
        if (response && response.content) {
          this.posts = response.content;
          this.pageInfo = response.page;
          console.log('Posts assigned:', this.posts.length, 'posts');
          console.log('Page info assigned:', this.pageInfo);
        } else {
          console.error('Invalid response structure:', response);
          this.error = 'Invalid response from server';
          this.posts = [];
          this.pageInfo = null;
        }
        
        console.log('Setting loading to false (success)');
        this.loading = false;
      },
      error: (error) => {
        console.error('ERROR: Error loading posts:', error);
        console.log('Error type:', typeof error);
        console.log('Error status:', error.status);
        console.log('Error message:', error.message);
        this.error = error.message || 'Failed to load posts';
        this.posts = [];
        this.pageInfo = null;
        console.log('Setting loading to false (error)');
        this.loading = false;
      },
      complete: () => {
        console.log('HTTP request completed');
        // Ensure loading is false
        if (this.loading) {
          console.warn('Loading was still true in complete, setting to false');
          this.loading = false;
        }
      }
    });
    
    // Add a timeout as a fallback
    setTimeout(() => {
      if (this.loading) {
        console.warn('Request timed out, setting loading to false');
        this.loading = false;
        this.error = 'Request timed out. Please check if the backend server is running.';
      }
    }, 10000); // 10 second timeout
  }

  goToPage(page: number): void {
    if (page >= 0 && this.pageInfo && page < this.pageInfo.totalPages) {
      this.loadPosts(page);
    }
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString();
  }

  getPageNumbers(): number[] {
    if (!this.pageInfo) return [];
    
    const total = this.pageInfo.totalPages;
    const current = this.pageInfo.number;
    const delta = 2; // Number of pages to show around current page
    
    let start = Math.max(0, current - delta);
    let end = Math.min(total - 1, current + delta);
    
    // Adjust if we're near the beginning or end
    if (current < delta) {
      end = Math.min(total - 1, end + (delta - current));
    }
    if (current + delta >= total) {
      start = Math.max(0, start - (current + delta - total + 1));
    }
    
    const pages = [];
    for (let i = start; i <= end; i++) {
      pages.push(i);
    }
    return pages;
  }

  trackByPage(index: number, page: number): number {
    return page;
  }
}
