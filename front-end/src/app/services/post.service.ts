import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { PostResponse, CreatePostRequest, UpdatePostRequest, PaginatedPostResponse } from '../model/post.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private readonly baseUrl = `${environment.apiUrl}/posts`;
  
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { 
    console.log('PostService initialized with baseUrl:', this.baseUrl);
  }

  /**
   * Get the base URL for debugging purposes
   */
  getBaseUrl(): string {
    return this.baseUrl;
  }

  /**
   * Get all posts (paginated)
   */
  getAllPosts(page: number = 0, size: number = 10): Observable<PaginatedPostResponse> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    const url = this.baseUrl;
    console.log('Making request to:', url);
    console.log('With params:', { page, size });

    return this.http.get<PaginatedPostResponse>(url, { params })
      .pipe(
        map(response => {
          console.log('Raw response:', response);
          return response;
        }),
        catchError(this.handleError)
      );
  }

  /**
   * Get all posts (non-paginated for backward compatibility)
   */
  getAllPostsNonPaginated(): Observable<PostResponse[]> {
    return this.getAllPosts(0, 1000).pipe(
      map(response => response.content)
    );
  }

  /**
   * Get a post by ID
   */
  getPostById(id: string): Observable<PostResponse> {
    return this.http.get<PostResponse>(`${this.baseUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Get a post by slug
   */
  getPostBySlug(slug: string): Observable<PostResponse> {
    return this.http.get<PostResponse>(`${this.baseUrl}/slug/${slug}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Get posts by category (paginated)
   */
  getPostsByCategory(categoryId: string, page: number = 0, size: number = 10): Observable<PaginatedPostResponse> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PaginatedPostResponse>(`${this.baseUrl}/category/${categoryId}`, { params })
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Get published posts only (paginated)
   */
  getPublishedPosts(page: number = 0, size: number = 10): Observable<PaginatedPostResponse> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PaginatedPostResponse>(`${this.baseUrl}/published`, { params })
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Create a new post
   */
  createPost(post: CreatePostRequest): Observable<PostResponse> {
    return this.http.post<PostResponse>(this.baseUrl, post, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Update an existing post
   */
  updatePost(id: string, post: UpdatePostRequest): Observable<PostResponse> {
    return this.http.put<PostResponse>(`${this.baseUrl}/${id}`, post, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Delete a post
   */
  deletePost(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Publish a post
   */
  publishPost(id: string): Observable<PostResponse> {
    return this.http.patch<PostResponse>(`${this.baseUrl}/${id}/publish`, {}, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Unpublish a post
   */
  unpublishPost(id: string): Observable<PostResponse> {
    return this.http.patch<PostResponse>(`${this.baseUrl}/${id}/unpublish`, {}, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Search posts by title or content (paginated)
   */
  searchPosts(query: string, page: number = 0, size: number = 10): Observable<PaginatedPostResponse> {
    const params = new HttpParams()
      .set('q', query)
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PaginatedPostResponse>(`${this.baseUrl}/search`, { params })
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Handle HTTP errors
   */
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Client Error: ${error.error.message}`;
    } else {
      // Server-side error
      if (error.status === 0) {
        errorMessage = 'Unable to connect to the server. Please check if the backend is running.';
      } else if (error.status === 404) {
        errorMessage = 'Resource not found.';
      } else if (error.status === 400) {
        errorMessage = 'Bad request. Please check your input.';
      } else if (error.status === 500) {
        errorMessage = 'Internal server error. Please try again later.';
      } else {
        errorMessage = `Server Error: ${error.status} - ${error.message}`;
      }
    }
    
    console.error('PostService Error:', error);
    return throwError(() => new Error(errorMessage));
  }
}
