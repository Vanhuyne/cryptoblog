export interface PostResponse {
  id: string;
  title: string;
  slug: string;
  content: string;
  summary: string;
  coverImageUrl: string;
  isPublished: boolean;
  categoryId: string;
  categoryName: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreatePostRequest {
  title: string;
  content: string;
  summary: string;
  coverImageUrl?: string;
  isPublished: boolean;
  categoryId: string;
}

export interface UpdatePostRequest {
  title?: string;
  content?: string;
  summary?: string;
  coverImageUrl?: string;
  isPublished?: boolean;
  categoryId?: string;
}

export interface PageInfo {
  size: number;
  number: number;
  totalElements: number;
  totalPages: number;
}

export interface PaginatedPostResponse {
  content: PostResponse[];
  page: PageInfo;
}
