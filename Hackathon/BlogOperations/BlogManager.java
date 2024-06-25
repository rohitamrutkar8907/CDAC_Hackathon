import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

class Category implements Serializable {
    private int categoryId;
    private String title;
    private String description;
    private LocalDateTime creationDateTime;

    public Category(int categoryId, String title, String description) {
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.creationDateTime = LocalDateTime.now();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }
}

class Blog implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String content;
    private int categoryID;

    public Blog(String title, String content, int categoryID) {
        this.title = title;
        this.content = content;
        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}

public class BlogManager {
    private static final String catfilename = "categories.txt";
    private static final String blogfilename = "blogs.txt";
    private static ArrayList<Blog> blogs = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Category> categories = new ArrayList<>();
    private static int categoryIdCounter = 1;

    public static void blogmenu() {
        loadCategoriesFromFile();
        loadBlogsFromFile();
        int choice;
        do {
            System.out.println("1. Show Categories");
            System.out.println("2. Add Category");
            System.out.println("3. Create Blog");
            System.out.println("4. Display All Blogs");
            System.out.println("5. Delete Blog");
            System.out.println("6. Read Blog Content");
            System.out.println("7. Edit Blog");
            System.out.println("8. Search Blog");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showCategories();
                    break;

                case 2:
                    addCategory();
                    break;

                case 3:
                    createBlog();
                    break;

                case 4:
                    displayAllBlogs();
                    break;
                case 5:
                    deleteBlog();
                    break;

                case 6:
                    readBlogContent();
                    break;

                case 7:
                    editBlog();
                    break;

                case 8:

                    searchBlog();
                    break;

                case 0:
                    System.out.println("Exiting program...");
                    saveCategoriesToFile();
                    saveBlogsToFile();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }

    private static void loadCategoriesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(catfilename))) {
            categories = (ArrayList<Category>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No categories found. Starting with an empty category list.");
        }
    }

    private static void loadBlogsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(blogfilename))) {
            blogs = (ArrayList<Blog>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No blogs found. Starting with an empty blog list.");
        }
    }

    private static void saveCategoriesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(catfilename))) {
            oos.writeObject(categories);
        } catch (IOException e) {
            System.out.println("Error saving categories to file.");
        }
    }

    private static void saveBlogsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(blogfilename))) {
            oos.writeObject(blogs);
        } catch (IOException e) {
            System.out.println("Error saving blogs to file.");
        }
    }

    private static void createBlog() {
        System.out.print("Enter blog title: ");
        String title = scanner.nextLine();
        System.out.print("Enter blog content: ");
        String content = scanner.nextLine();

        System.out.print("Enter category ID for the blog: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        boolean categoryExists = false;
        for (Category category : categories) {
            if (category.getCategoryId() == categoryId) {
                categoryExists = true;
                break;
            }
        }

        if (categoryExists) {
            Blog blog = new Blog(title, content, categoryId);
            blogs.add(blog);
            System.out.println("Blog created successfully.");
        } else {
            System.out.println("Category with ID " + categoryId + " not found. Please create the category first.");
        }
    }

    private static void displayAllBlogs() {

        if (blogs.isEmpty()) {
            System.out.println("No blogs found.");
        } else {
            System.out.println("All Blogs:");
            for (Blog blog : blogs) {
                System.out.println(blog.getTitle());
            }
        }
    }

    private static void editBlog() {
        System.out.print("Enter blog title to edit: ");
        String title = scanner.nextLine();
        boolean found = false;
        for (Blog blog : blogs) {
            if (blog.getTitle().equalsIgnoreCase(title)) {
                System.out.print("Enter new content: ");
                String newContent = scanner.nextLine();
                blog.setContent(newContent);
                found = true;
                System.out.println("Blog content updated successfully.");
                break;
            }
        }
        if (!found) {
            System.out.println("Blog not found.");
        }
    }

    private static void searchBlog() {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        boolean found = false;
        for (Blog blog : blogs) {
            if (blog.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("Blog Title: " + blog.getTitle());
                System.out.println("Blog Content: " + blog.getContent());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No blogs found matching the search keyword.");
        }
    }

    private static void deleteBlog() {
        System.out.print("Enter blog title to delete: ");
        String title = scanner.nextLine();
        boolean found = false;
        for (Blog blog : blogs) {
            if (blog.getTitle().equalsIgnoreCase(title)) {
                blogs.remove(blog);
                found = true;
                System.out.println("Blog deleted successfully.");
                break;
            }
        }
        if (!found) {
            System.out.println("Blog not found.");
        }
    }

    private static void readBlogContent() {
        System.out.print("Enter blog title to read: ");
        String title = scanner.nextLine();
        boolean found = false;
        for (Blog blog : blogs) {
            if (blog.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Blog Content:");
                System.out.println(blog.getContent());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Blog not found.");
        }
    }

    private static void addCategory() {
        System.out.print("Enter category title: ");
        String title = scanner.nextLine();
        System.out.print("Enter category description: ");
        String description = scanner.nextLine();

        Category category = new Category(categoryIdCounter, title, description);
        categories.add(category);
        categoryIdCounter++;
        System.out.println("Category added successfully.");
    }

    private static void showCategories() {
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            System.out.println("All Categories:");
            for (Category category : categories) {
                System.out.println("Category ID: " + category.getCategoryId());
                System.out.println("Title: " + category.getTitle());
                System.out.println("Description: " + category.getDescription());
                System.out.println("Creation Date Time: " + category.getCreationDateTime());
                System.out.println();
            }
        }
    }

}
