
---

## 🧩 Database Design (Entities)

### USER
- id
- username
- email
- role

### PRODUCT
- id
- name
- description
- price
- stock

### CART_ITEM
- id
- userId
- productId
- quantity

### ORDER
- id
- userId
- totalAmount
- status (CREATED, PAID, CANCELLED)
- createdAt

### ORDER_ITEM
- id
- orderId
- productId
- quantity
- price

### PAYMENT
- id
- orderId
- amount
- status (PENDING, SUCCESS, FAILED)
- paymentId
- createdAt

---

## 🔗 Relationships

- User → CartItem (1:N)
- User → Order (1:N)
- Order → OrderItem (1:N)
- Order → Payment (1:1)
- Product → CartItem (1:N)
- Product → OrderItem (1:N)

> MongoDB references are used instead of joins.

---

## 📌 APIs Implemented

### 🛍️ Product APIs
| Method | Endpoint | Description |
|------|---------|-------------|
| POST | /api/products | Create product |
| GET | /api/products | List products |
| GET | /api/products/{id} | Get product by ID |

---

### 🧺 Cart APIs
| Method | Endpoint | Description |
|------|---------|-------------|
| POST | /api/cart/{userId}/add/{productId} | Add to cart |
| GET | /api/cart/{userId} | View cart |
| DELETE | /api/cart/{userId}/remove/{productId} | Remove item |
| DELETE | /api/cart/{userId}/clear | Clear cart |

---

### 📦 Order APIs
| Method | Endpoint | Description |
|------|---------|-------------|
| POST | /api/orders | Place order from cart |
| GET | /api/orders/{orderId} | Get order by ID |
| GET | /api/orders/user/{userId} | Get order history (Bonus) |

---

### 💳 Payment APIs (Mock)
| Method | Endpoint | Description |
|------|---------|-------------|
| POST | /api/payments/create | Initiate payment |
| POST | /api/webhooks/payment | Payment webhook callback |

---

## 🔄 Complete Order Flow

1. Create products
2. Add products to cart
3. View cart
4. Place order from cart
5. Initiate payment
6. Mock payment service waits 3 seconds
7. Webhook updates payment & order status
8. Order status becomes **PAID**

---

## 🧪 Postman Testing Flow

1. **Create Product**
2. **Add to Cart**
3. **View Cart**
4. **Place Order**
5. **Create Payment**
6. **Wait 3 seconds**
7. **Fetch Order → Status = PAID**
8. **Fetch Order History**

Postman variables used:
- `userId`
- `productId`
- `orderId`

---

## ▶️ How to Run the Project

### Prerequisites
- Java 17+
- MongoDB running on `localhost:27017`
- Maven

### Steps
```bash
git clone <repository-url>
cd ecommerce
mvn clean install
mvn spring-boot:run
