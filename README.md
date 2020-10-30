# TOURISM APP (Android Native)

APP DESCRIPTION:
The app promotes and encourages tourism within the city of Toronto by show casing different tourist spots around the city.
The App was made using Android Studio, Java and XML. The database consists of five tourist spots and four users.

#### LOGIC IMPLEMENTATION:
1. Where required, used object oriented programming principles 
2. Data between screens was be passed using intents.  
3. Data persisted with SharedPreferences and JSON files.

#### USER INTERFACE:
Provided navigation drawers and toolbars. 
Multiple screens implemented using navigation as well as intents. 
List views show tourist attractions. 
Buttons that naviagte you to web views.


#### FEATURES OF THE APP:
1. User account login 
2. Provided a screen that would  allow users to login by entering the below credentials : Username and Password
3. Added a Remember me checkbox-  If the user has this checked, they will be automatically logged in and navigated to the Tourist Attraction List screen.
The app can validate with the below users. Note, users are statically added and loaded from a JSON file.

#### USERNAMES AND PASSWORDS
1. thanos@gmail.com  -  1234
2. wonderwoman@yahoo.com - abc00021
3. jonsnow@winteriscoming.com - gameofthrones2
4. superman@kypton.com - kk11iii



#### TOURIST ATTRACTION LIST:
1. A list of popular tourist attractions in the city. 
2. This list shows the attraction name, address and photo. 
3. Implemented using a custom List View and Adapter. 
4. Tourist attractions were loaded from a JSON file stored in the assets folder. 

#### WISH LIST:
1. The app allows users to add a tourist attraction to their “wish list” of things to see. 
2. It is clear from the UI which attractions are added to the wish list. 
3. If the user closes the app and relaunches the app, the app remembers which attraction was on the wishlist. 
4. Implemented using a List View.


#### ATTRACTION DETAILS SCREEN:
When a user selects an attraction from the list, the app displays a screen that shows more detailed information about the attraction. 
On this page you can see the following:
1. name
2. address,
3. phone no: Clicking on the phone number automatically opens the phone dialer so the user can attempt to call said place.
4. website: Clicking on the website automatically opens a web view showing the attraction web page.
5. more photos
6. description
7. pricing 
8. ratings: Users are able to leave a star rating for the tourist attraction. Implemented using a “Rating Bar” view.


#### GENERAL UI FEATURES:
The app has a navigation drawer and the navigation drawer contains the following:-
1. Your Wish List - This will naviagte the user to their wish list. If it has no items to show then the screen will say "Your wish list is empty!"
2. Tourist Attraction List - This will navigate the user to the Tourist Navigation List
3. Map -  For this screen, a web view is loaded that displays a  bing.com map that is centered to a random location. This was implemented using https://www.bing.com/maps.
4. Logout  - Clicking this link will logout the user and redirect them back to the Login screen. 



