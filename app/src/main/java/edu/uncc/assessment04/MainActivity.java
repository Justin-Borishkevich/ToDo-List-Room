package edu.uncc.assessment04;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.assessment04.fragments.todo.AddItemToToDoListFragment;
import edu.uncc.assessment04.fragments.users.AddUserFragment;
import edu.uncc.assessment04.fragments.todo.CreateNewToDoListFragment;
import edu.uncc.assessment04.fragments.users.PassCodeFragment;
import edu.uncc.assessment04.fragments.todo.ToDoListDetailsFragment;
import edu.uncc.assessment04.fragments.todo.ToDoListsFragment;
import edu.uncc.assessment04.fragments.users.UsersFragment;
import edu.uncc.assessment04.models.AppDatabase;
import edu.uncc.assessment04.models.ToDoList;
import edu.uncc.assessment04.models.ToDoListItem;
import edu.uncc.assessment04.models.User;

public class MainActivity extends AppCompatActivity implements UsersFragment.UsersListener, AddUserFragment.AddUserListener,
        PassCodeFragment.PassCodeListener, ToDoListsFragment.ToDoListsListener, ToDoListDetailsFragment.ToDoListDetailsListener,
        CreateNewToDoListFragment.CreateNewToDoListListener, AddItemToToDoListFragment.AddItemToListListener {
    User currentUser;

    final String TAG = "demo";

    AppDatabase db;

    ArrayList<User> mUsers = new ArrayList<User>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = Room.databaseBuilder(this, AppDatabase.class, "user.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        if(currentUser == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new UsersFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new ToDoListsFragment())
                    .commit();
        }
    }

//    private void reloadLists() {
//        try {
//            List<User> users = db.userDao().getAll();
//            mUsers.clear();
//            mUsers.addAll(users);
//            Log.d(TAG, "Reloaded users: " + mUsers.size());
//            if (!mUsers.isEmpty()) {
//                Log.d(TAG, "First user after reload: " + mUsers.get(0));
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "Error reloading users: " + e.getMessage(), e);
//        }
//    }

    //UsersFragment.UsersListener
    @Override
    public void gotoEnterPassCode(User user) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, PassCodeFragment.newInstance(user))
                .commit();
    }

    //UsersFragment.UsersListener
    @Override
    public void gotoAddUser() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new AddUserFragment())
                .commit();
    }

    //UsersFragment.UsersListener
    @Override
    public ArrayList<User> getAllUsers() {
        List<User> users = db.userDao().getAll();
        ArrayList<User> userss = new ArrayList<>();
        userss.addAll(users);
        Log.d(TAG, "getAllUsers called, returning " + userss.size() + " users");
        return userss;
    }

    //AddUserFragment.AddUserListener
    @Override
    public void saveNewUser(User user) {
        try {
            db.userDao().insertAll(user);
            Log.d(TAG, "User " + user.getName() + " Successfully Entered");
        } catch (Exception e) {
            Log.e(TAG, "Error inserting new user: " + e.getMessage(), e);
        }


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new UsersFragment())
                .commit();
    }

    //AddUserFragment.AddUserListener
    @Override
    public void cancelAddUser() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new UsersFragment())
                .commit();
    }

    //PassCodeFragment.PassCodeListener
    @Override
    public void onPasscodeSuccessful(User user) {
        currentUser = user;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new ToDoListsFragment())
                .commit();
    }

    //PassCodeFragment.PassCodeListener
    @Override
    public void onPassCodeCancel() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new UsersFragment())
                .commit();
    }

    //ToDoListsFragment.ToDoListsListener
    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    //ToDoListsFragment.ToDoListsListener
    @Override
    public void gotoAddNewToDoList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new CreateNewToDoListFragment())
                .commit();
    }

    //ToDoListsFragment.ToDoListsListener
    @Override
    public void logout() {
        currentUser = null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new UsersFragment())
                .commit();
    }

    //ToDoListsFragment.ToDoListsListener
    @Override
    public void gotoListDetails(ToDoList toDoList) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, ToDoListDetailsFragment.newInstance(toDoList))
                .commit();
    }

    //ToDoListsFragment.ToDoListsListener
    @Override
    public ArrayList<ToDoList> getAllToDoListsForUser(User user) {
        List<ToDoList> lists = db.listDao().findUserLists(user.getId());
        ArrayList<ToDoList> listss = new ArrayList<>();
        listss.addAll(lists);
        Log.d(TAG, "getAllToDoListsForUser called, returning " + listss.size() + " users");
        return listss;
    }

    //ToDoListsFragment.ToDoListsListener
    @Override
    public void deleteToDoList(ToDoList toDoList) {
        try {
            db.listDao().delete(toDoList);
            Log.d(TAG, "Deleted ToDoList: " + toDoList.getName());
        } catch (Exception e) {
            Log.e(TAG, "Error deleting ToDoList: " + e.getMessage(), e);
        }
    }

    //ToDoListDetailsFragment.ToDoListDetailsListener
    @Override
    public void gotoAddListItem(ToDoList toDoList) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, AddItemToToDoListFragment.newInstance(toDoList))
                .commit();
    }

    //ToDoListDetailsFragment.ToDoListDetailsListener
    @Override
    public ArrayList<ToDoListItem> getAllItemsForToDoList(ToDoList toDoList) {
        List<ToDoListItem> items = db.listItemDao().findListItems(toDoList.getId());
        ArrayList<ToDoListItem> itemss = new ArrayList<>();
        itemss.addAll(items);
        Log.d(TAG, "getAllItemsForToDoList called, returning " + itemss.size() + " items");
        return itemss;
    }

    //ToDoListDetailsFragment.ToDoListDetailsListener
    @Override
    public void goBackToToDoLists() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new ToDoListsFragment())
                .commit();
    }

    //ToDoListDetailsFragment.ToDoListDetailsListener
    @Override
    public void deleteToDoListItem(ToDoList toDoList, ToDoListItem toDoListItem) {
        //TODO: Delete the ToDoListItem from the database
        try {
            db.listItemDao().delete(toDoListItem);
            Log.d(TAG, "Deleted ToDoListItem: " + toDoListItem.getName());
        } catch (Exception e) {
            Log.e(TAG, "Error deleting ToDoListItem: " + e.getMessage(), e);
        }
    }

    //CreateNewToDoListFragment.CreateNewToDoListListener
    @Override
    public void onCreateNewToDoList(User user, String listName) {
        try {
            db.listDao().insertAll(new ToDoList(listName, user.getId()));
            Log.d(TAG, "List " + listName + " belonging to " + user.getName() + " Successfully Entered");
        } catch (Exception e) {
            Log.e(TAG, "Error inserting new ToDoList: " + e.getMessage(), e);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new ToDoListsFragment())
                .commit();
    }

    //CreateNewToDoListFragment.CreateNewToDoListListener
    @Override
    public void onCancelCreateNewToDoList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new ToDoListsFragment())
                .commit();
    }

    //AddItemToToDoListFragment.AddItemToListListener
    @Override
    public void onAddItemToList(User user, ToDoList todoList, String itemName, String priority) {
        try {
            db.listItemDao().insertAll(new ToDoListItem(itemName, priority, todoList.getId(), user.getId()));
            Log.d(TAG, "List item " + itemName + " in " + todoList.getName() + " belonging to " + user.getName() + " Successfully Entered");
        } catch (Exception e) {
            Log.e(TAG, "Error inserting new ToDoList: " + e.getMessage(), e);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, ToDoListDetailsFragment.newInstance(todoList))
                .commit();
    }

    //AddItemToToDoListFragment.AddItemToListListener
    @Override
    public void onCancelAddItemToList(ToDoList todoList) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, ToDoListDetailsFragment.newInstance(todoList))
                .commit();
    }
}