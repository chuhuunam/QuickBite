# Frontend Standards - Angular 19

## 1. Project Structure

```
web-app/src/app/
├── core/                    # Singleton services, guards, interceptors
│   ├── services/
│   ├── guards/
│   ├── interceptors/
│   └── models/
├── shared/                  # Shared components, pipes, directives
│   ├── components/
│   ├── pipes/
│   └── directives/
├── features/                # Feature modules (lazy loaded)
│   ├── auth/
│   ├── home/
│   ├── restaurant/
│   └── order/
└── layouts/                 # Layout components
    ├── main-layout/
    └── auth-layout/
```

## 2. Component Pattern (Standalone + Signals)

```typescript
import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-restaurant-list',
  standalone: true,
  imports: [CommonModule, RestaurantCardComponent],
  templateUrl: './restaurant-list.component.html',
  styleUrl: './restaurant-list.component.scss'
})
export class RestaurantListComponent {
  // Inject services
  private restaurantService = inject(RestaurantService);
  
  // State với signals
  restaurants = signal<Restaurant[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);
  searchTerm = signal('');
  
  // Computed values
  filteredRestaurants = computed(() => {
    const term = this.searchTerm().toLowerCase();
    return this.restaurants().filter(r => 
      r.name.toLowerCase().includes(term)
    );
  });
  
  constructor() {
    this.loadRestaurants();
  }
  
  private loadRestaurants(): void {
    this.loading.set(true);
    this.error.set(null);
    
    this.restaurantService.getAll()
      .pipe(takeUntilDestroyed())
      .subscribe({
        next: (data) => {
          this.restaurants.set(data);
          this.loading.set(false);
        },
        error: (err) => {
          this.error.set('Failed to load restaurants');
          this.loading.set(false);
        }
      });
  }
  
  onSearch(term: string): void {
    this.searchTerm.set(term);
  }
}
```

## 3. Service Pattern

```typescript
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';

@Injectable({ providedIn: 'root' })
export class RestaurantService {
  private http = inject(HttpClient);
  private baseUrl = `${environment.apiUrl}/api/restaurants`;
  
  getAll(): Observable<Restaurant[]> {
    return this.http.get<Restaurant[]>(this.baseUrl);
  }
  
  getById(id: string): Observable<Restaurant> {
    return this.http.get<Restaurant>(`${this.baseUrl}/${id}`);
  }
  
  create(data: CreateRestaurantRequest): Observable<Restaurant> {
    return this.http.post<Restaurant>(this.baseUrl, data);
  }
  
  update(id: string, data: UpdateRestaurantRequest): Observable<Restaurant> {
    return this.http.put<Restaurant>(`${this.baseUrl}/${id}`, data);
  }
  
  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
```

## 4. Smart/Dumb Component Pattern

```typescript
// Smart Component (Container) - handles logic
@Component({
  selector: 'app-order-page',
  standalone: true,
  imports: [OrderListComponent, OrderFilterComponent],
  template: `
    <app-order-filter (filterChange)="onFilterChange($event)" />
    <app-order-list 
      [orders]="filteredOrders()" 
      [loading]="loading()"
      (orderClick)="onOrderClick($event)"
    />
  `
})
export class OrderPageComponent {
  // All logic here
}

// Dumb Component (Presentational) - only displays
@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    @if (loading()) {
      <app-loading />
    } @else {
      @for (order of orders(); track order.id) {
        <app-order-card [order]="order" (click)="orderClick.emit(order)" />
      }
    }
  `
})
export class OrderListComponent {
  orders = input.required<Order[]>();
  loading = input(false);
  orderClick = output<Order>();
}
```

## 5. Forms (Reactive)

```typescript
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  template: `
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <input formControlName="email" />
      @if (form.get('email')?.errors?.['required']) {
        <span class="error">Email is required</span>
      }
      <button type="submit" [disabled]="form.invalid || submitting()">
        Submit
      </button>
    </form>
  `
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  submitting = signal(false);
  
  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });
  
  onSubmit(): void {
    if (this.form.valid) {
      this.submitting.set(true);
      // submit logic
    }
  }
}
```

## 6. HTTP Interceptors

```typescript
// auth.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();
  
  if (token) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }
  
  return next(req);
};

// error.interceptor.ts
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        inject(AuthService).logout();
        inject(Router).navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};

// app.config.ts
export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor, errorInterceptor]))
  ]
};
```

## 7. Routing (Lazy Loading)

```typescript
// app.routes.ts
export const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { 
        path: 'home', 
        loadComponent: () => import('./features/home/home.component')
          .then(m => m.HomeComponent)
      },
      {
        path: 'restaurants',
        loadChildren: () => import('./features/restaurant/restaurant.routes')
          .then(m => m.RESTAURANT_ROUTES)
      }
    ]
  },
  {
    path: 'auth',
    loadChildren: () => import('./features/auth/auth.routes')
      .then(m => m.AUTH_ROUTES)
  }
];
```

## 8. Common Mistakes to Avoid

- ❌ Không subscribe trong subscribe → ✅ Dùng switchMap, mergeMap
- ❌ Không quên unsubscribe → ✅ Dùng takeUntilDestroyed()
- ❌ Không dùng any → ✅ Define proper types
- ❌ Không để logic trong template → ✅ Dùng computed signals
- ❌ Không import whole modules → ✅ Import specific components
