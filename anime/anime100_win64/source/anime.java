import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class anime extends PApplet {

ArrayList<PImage> images = new ArrayList<PImage>();
ArrayList<String> imagesName = new ArrayList<String>();
String[] extensions = {
  ".jpg", ".gif", ".tga", ".png", ".jpeg"
};

PFont font;


int pages = 0;

public void setup() {
  
  selectFolder("画像のあるフォルダを選んでください", "loadImages");
  font = createFont("Arial", 20);
  textFont(font);
  textSize(20);
  strokeWeight(3);
}

public void loadImages(File selection) {
  File[] files = selection.listFiles();
  for (int i = 0; i < files.length; i++) {
    for (String extension : extensions) {
      if (files[i].getPath().endsWith(extension)) {
        PImage img = loadImage(files[i].getAbsolutePath());
        ;
        images.add(fitSize(img));
        imagesName.add(files[i].getAbsolutePath());
      }
    }
  }
  
  PImage[] tempImage = new PImage[images.size()];
  String[] tempStr = new String[imagesName.size()];
  for(int k = 0; k < tempImage.length; k++){
    tempImage[k] = images.get(k);
    tempStr[k] = imagesName.get(k);
  }
  
  for(int k = 0; k < tempImage.length; k++){
    String [] ttt = split(tempStr[k], ".");
    String [] tttt = split(ttt[0], "/");
    String [] ttttt = split(tttt[tttt.length-1], ",");
    imagesName.set(PApplet.parseInt(ttttt[0])-1, tempStr[k]);
    images.set(PApplet.parseInt(ttttt[0])-1, tempImage[k]);
  }

  pages = images.size()/25+1;
}

public PImage fitSize(PImage img) {
  PImage img2;
  if(img.height>img.width){
    img2 = img.get(0,PApplet.parseInt(img.height/2.0f-((img.width/16.0f)*9.0f)/2.0f),img.width,PApplet.parseInt((img.width/16.0f)*9.0f));
  } else {
    img2 = img.get(PApplet.parseInt(img.width/2.0f-((img.height/9.0f)*16.0f)/2.0f),0,PApplet.parseInt((img.height/9.0f)*16.0f),img.height);
  }
  img2.resize(1280/5, 720/5);
  
  return img2;
}

int i = 0;

public void draw() {
  if (images.size() > 0 && i < pages) {
    background(0);
    int jj = 25;
    if(pages-1 == i)jj = images.size()-25*(pages-1);
      for (int j = 0; j < jj; j++) {
        image(images.get(i*25+j), (1280/5)*(j%5), (j/5)*720/5);
        textAlign(RIGHT, BOTTOM);
        String [] temp = split(imagesName.get(i*25+j), ".");
        String [] temp2 = split(temp[0], ",");
        noStroke();
        fill(0);
        int textsize=20;
        textSize(textsize);
        while(textWidth(temp2[1])>1280/5){
             textsize--;
             textSize(textsize);
        }
        fill(0,0,0,200);
        rect((1280/5)*(j%5)+1280/5, ((j/5)+1)*720/5, -textWidth(temp2[1]), -(textAscent()+textDescent()));
        fill(255);
        text(temp2[1], (1280/5)*(j%5)+1280/5, ((j/5)+1)*720/5);
        
      }
      save(str(i) + "," + str(second()) + str(millis()) + ".png");
    i++;
  }
}
  public void settings() {  size(1280, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "anime" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
