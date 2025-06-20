# MangaReaderApp 📖

A manga/comic reader clone Android app with a PHP backend for managing your digital library.

![App Screenshot](https://via.placeholder.com/300x600?text=MangaReaderApp+Preview) 
remember to upload vid 

## Features ✨

### Android App
- Browse and read manga/comics
- Light/dark theme

### PHP Backend
- User authentication (login/register)
- Manga metadata management (update/delete)
- Image hosting for covers/pages

## Tech Stack 🛠️

- **Frontend**: Android (Java/Groovy.dsl)
- **Backend**: PHP + MySQL
- **Database**: MySQL (sample schema included)

## Setup Guide 🚀

### Prerequisites
- Android Studio (for app)
- XAMPP/WAMP (for local PHP testing)
- MySQL database

### 1. Android App

```
git clone https://github.com/SaiffullahKhan/MangaReaderApp.git
# Open in Android Studio
```

### 2. PHP Backend

1. Place `php-backend` folder in your web server (e.g., XAMPP's `htdocs`)

2. Import database:
```
mysql -u username -p < database-setup/mangareader.sql
```

3. Configure credentials in `php-backend/db_connect.php`:
```php
define('DB_USER', 'your_username');
define('DB_PASS', 'your_password');
```

### 3. Connect App to Backend

Update IP Address in Android app's `MainActivity.java`:
```java
public static String ipAddress = "http://your-local-ip/php-backend/";
```

**Note**: This is a personal project. Database credentials in `db_connect.php` are configured for local development. 
