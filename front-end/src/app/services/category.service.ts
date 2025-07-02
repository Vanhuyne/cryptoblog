import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Category, CreateCategoryRequest, UpdateCategoryRequest } from '../model/category.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private readonly baseUrl = `${environment.apiUrl}/categories`;
  
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  /**
   * Get all categories
   */
  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.baseUrl)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Get a category by ID
   */
  getCategoryById(id: string): Observable<Category> {
    return this.http.get<Category>(`${this.baseUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Get a category by slug
   */
  getCategoryBySlug(slug: string): Observable<Category> {
    return this.http.get<Category>(`${this.baseUrl}/slug/${slug}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Create a new category
   */
  createCategory(category: CreateCategoryRequest): Observable<Category> {
    return this.http.post<Category>(this.baseUrl, category, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Update an existing category
   */
  updateCategory(id: string, category: UpdateCategoryRequest): Observable<Category> {
    return this.http.put<Category>(`${this.baseUrl}/${id}`, category, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Delete a category
   */
  deleteCategory(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`)
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
    
    console.error('CategoryService Error:', error);
    return throwError(() => new Error(errorMessage));
  }
}
