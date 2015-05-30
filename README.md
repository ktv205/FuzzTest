# FuzzTest
Simple app to load images and text from a json source
The app has Four Activities
1.HelperActivity
2.MainActivity
3.ImageActivity
4.WebViewActivity


1.HelperActivity
  ->This is the laucher activity
  ->Through this activity I am downloading all the data from json source
  ->I am calling DataAsyncTask to download the data in the background
  ->I am calling HttpManager class from DataAsyncTask to download the json data
  -> I am using DataJsonParser class to parse the data
  ->Once the data is parsed I am getting the data in ArrayList of DataModel objects back to HelperActivity class
    via an ServerDataInterface inside DataAsyncTask
  ->Once I got the objects I separate Image data and text data
  ->I download the images from image data url and save them in pictures public directory
  ->Once I saved the pictures I save the picture path in DataModel
  ->After all this is done I am sending the user to MainActivity

2.MainActivity
  -> Here I get the data from Intent via HelperActivity
  -> I have a FragmentStatePagerAdapter class that create three tabs 
     ->All
     ->Text
     ->Images
  ->Once I have these three tabs clicking on each of them will send a call to MainFragment
  ->Based on tabs click I send data to MainFragment i.e all the data or Image data or textData via setArguments
  ->MainFragment
    ->Here I send the data I got from MainActivity to MainRecyclerViewAdapter
    ->MainRecyclerViewAdapter
        ->Here the actual work is done i.e showing appropriate data in appropriate way
        -> If it is all the data or text data I am showing in a list format with the help of 
          recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ->If it is only image data I am showing in grid format with 3 images in a row
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            
3.ImageActivity
  -> Whenever user clicks on a image he will be send to this activity with image path in the intent extras
  ->This activity is the child activity of mainactivity

4.WebViewActivity
  ->Whenever user clicks on a text I am showing the http://quizzes.fuzzstaging.com/quizzes/mobile/1 in a webview
  ->This activity is the child activity of mainactivity

5.OtherClasses
  ->RequestParams: This is a model to set the url and set the type of the request and set get or post variables if any are present
  ->AppConstants: This is a class to create different contants that can be used across the app
  ->MainRecyclerViewDecorator:This class is used to create a divider line between list items
  
->I used toolbar instead of actionbar

Challenges I faced:
->There are some image urls that returned 404 errors
-> Some of the image urls took very long time to load but only to give timedout error
-> Some images are too big i.e 14mb I had to scale down those pictures using Bitmap api in android


Improvements that can be made:
-> There is always possibility to refactor the code and improve the quality of the code
-> Comments for each class and method can be added to improve readabilty
-> Images downloading method can be imrpoved
-> Showing of images can be imrpoved i.e change the view in potrait mode,landscape mode,tablets,phones etc

