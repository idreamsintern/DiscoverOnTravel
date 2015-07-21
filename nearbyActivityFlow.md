# NearbyActivity function call graph
### Click on Nearby Place
1.  NearbyActivity           . onCreate(Bundle savedInstanceState): null
2.  NavigationDrawerFragment . onAttach(Activity activity): NearbyActivity@4022
3.  NavigationDrawerFragment . onCreate(Bundle savedInstanceState): null
4.  NavigationDrawerFragment . selectItem(String mKey, String mCategory): null, null
5.  NearbyActivity           . onNavigationDrawerItemSelected(String mCategory, String mKey): null, null
6.  PlaceholderFragment      . newInstance(String category, String key): null, null
7.  NavigationDrawerFragment . setUp(int fragmentId, DrawerLayout drawerLayout): 2131689606, DrawerLayout
8.  NavigationDrawerFragment . onActivityCreated(Bundle savedInstanceState): null
9.  PlaceholderFragment      . onAttach(Activity activity): NearbyActivity@4022
10. NearbyActivity           . onSectionAttached(String category, String key): null, null
11. PlaceholderFragment      . onCreate(Bundle savedInstanceState): null
12. PlaceholderFragment      . onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
                               : PhoneLayoutInflater@4190, FrameLayout"app:id=container", null
13. PlaceholderFragment      . onActivityCreated(Bundle savedInstanceState): null
14. PlaceholderFragment      . onCreateLoader(int i, Bundle bundle): 0, null
15. NearbyActivity           . nCreateOptionsMenu(Menu menu): MenuBuilder@4243
16. NavigationDrawerFragment . isDrawerOpen()
17. NearbyActivity           . restoreActionBar()
18. NavigationDrawerFragment . isDrawerOpen()
19. PlaceholderFragment      . onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
                               : CursorLoader{1c0a1f11 id=0}, ContentResolver$CursorWrapperInner@4303

### Clicking on the navigation drawer button
1.  NearbyActivity           . onOptionsItemSelected(MenuItem item): ActionMenuItem@4339
2.  NearbyActivity           . onCreateOptionsMenu(Menu menu): MenuBuilder@4243
3.  NavigationDrawerFragment . isDrawerOpen()
4.  NavigationDrawerFragment . isDrawerOpen()

### Clicking on the navigation drawer item food->肉
1.  NavigationDrawerFragment . selectItem(String key, String category): food, 肉
2.  NearbyActivity           . onNavigationDrawerItemSelected(String category, String key): food, 肉
3.  PlaceholderFragment      . newInstance(String category, String key): food, 肉
4.  PlaceholderFragment      . onLoaderReset(Loader<Cursor> cursorLoader): CursorLoader{1c0a1f11 id=0}
9.  PlaceholderFragment      . onAttach(Activity activity): NearbyActivity@4022
10. NearbyActivity           . onSectionAttached(String category, String key): food, 肉
11. PlaceholderFragment      . onCreate(Bundle savedInstanceState): null
12. PlaceholderFragment      . onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
                               : PhoneLayoutInflater@4477, FrameLayout"app:id=container", null
13. PlaceholderFragment      . onActivityCreated(Bundle savedInstanceState): null
14. PlaceholderFragment      . onCreateLoader(int i, Bundle bundle): 0, null
15. NearbyActivity           . nCreateOptionsMenu(Menu menu): MenuBuilder@4243
16. NavigationDrawerFragment . isDrawerOpen()
17. NearbyActivity           . restoreActionBar()
18. NavigationDrawerFragment . isDrawerOpen()
19. PlaceholderFragment      . onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
                               : CursorLoader{2c594f3a id=0}, ContentResolver$CursorWrapperInner@4567
