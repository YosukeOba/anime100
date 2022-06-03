ArrayList<PImage> images = new ArrayList<PImage>();
ArrayList<String> imagesName = new ArrayList<String>();
String[] extensions = {
  ".jpg", ".gif", ".tga", ".png", ".jpeg"
};

PFont font;


int pages = 0;

void setup() {
  size(1280, 720);
  selectFolder("画像のあるフォルダを選んでください", "loadImages");
  font = createFont("Arial", 20);
  textFont(font);
  textSize(20);
  strokeWeight(3);
}

void loadImages(File selection) {
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
    imagesName.set(int(ttttt[0])-1, tempStr[k]);
    images.set(int(ttttt[0])-1, tempImage[k]);
  }

  pages = images.size()/25+1;
}

PImage fitSize(PImage img) {
  PImage img2;
  if(img.height>img.width){
    img2 = img.get(0,int(img.height/2.0-((img.width/16.0)*9.0)/2.0),img.width,int((img.width/16.0)*9.0));
  } else {
    img2 = img.get(int(img.width/2.0-((img.height/9.0)*16.0)/2.0),0,int((img.height/9.0)*16.0),img.height);
  }
  img2.resize(1280/5, 720/5);
  
  return img2;
}

int i = 0;

void draw() {
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
