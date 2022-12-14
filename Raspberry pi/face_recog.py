import time
import RPi.GPIO as GPIO
import playsound
from picamera import PiCamera
from time import sleep
import datetime
import sys, os
import requests
import firebase_admin
from firebase_admin import credentials
from firebase_admin import storage
from firebase_admin import db
from uuid import uuid4
import schedule
import cv2




GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
PIR_PIN = 17
GPIO.setup(PIR_PIN, GPIO.IN, pull_up_down = GPIO.PUD_DOWN)

door = 24
GPIO.setup(door, GPIO.OUT, initial = GPIO.HIGH)
input_prev=0

recognizer = cv2.face.LBPHFaceRecognizer_create()
recognizer.read('/home/colpop/capstone/trainer/trainer.yml')
cascadePath = ("/home/colpop/opencv/opencv-4.1.2/data/haarcascades/haarcascade_frontalface_default.xml")
faceCascade = cv2.CascadeClassifier(cascadePath);

#--------------------------------------

PROJECT_ID = "colpop1-d1ca3"
#my project id
 
cred = credentials.Certificate("/home/colpop/다운로드/colpop1-d1ca3-firebase-adminsdk-gt1kc-a5669ab4d9.json") #(키 이름 ) 부분에 본인의 키이름을 적어주세요.
default_app = firebase_admin.initialize_app(cred, {
    'storageBucket': 'colpop1-d1ca3.appspot.com',
    'databaseURL': 'https://colpop1-d1ca3-default-rtdb.firebaseio.com'
    })#버킷은 바이너리 객체의 상위 컨테이너이다. 버킷은 Storage에서 데이터를 보관하는 기본 컨테이너이다.

config = {
    "apiKey": "AIzaSyDT-Q8XSSdEu0yz922HeD3Zjg_XX4rCJl4",
    "authDomain": "colpop1-d1ca3.firebaseapp.com",
    "databaseURL": "https://colpop1-d1ca3-default-rtdb.firebaseio.com",
    "storageBucket": "colpop1-d1ca3.appspot.com"
}

bucket = storage.bucket('colpop1-d1ca3.appspot.com')#기본 버킷 사용



def fileUpload(file):
    blob = bucket.blob(file) #저장한 사진을 파이어베이스 storage의 image_store라는 이름의 디렉토리에 저장
    #new token and metadata 설정
    new_token = uuid4()
    metadata = {"firebaseStorageDownloadTokens": new_token} #access token이 필요하다.
    blob.metadata = metadata    
    blob.upload_from_filename(filename='/home/colpop/image_store/'+file, content_type='image/jpeg') #파일이 저장된 주소와 이미지 형식(jpeg도 됨)   
    
    a = "https://firebasestorage.googleapis.com/v0/b/" + 'colpop1-d1ca3.appspot.com' + '/o/' + str(file) + "?alt=media"
    print(a)
    
    time = str(datetime.datetime.now().strftime("%Y"+"년 "+"%m"+"월 "+"%d"+"일 "+"%H:%M:%S")) #월년일
    
    name = str(id)
    
    ref = db.reference('Image')
    ref.push({
        'imageUrl': a,
        'time' : time,
        'name' : name
    })
    
    a = db.reference('Noti')
    a.update({
        'noti' : 1
    })


    

#--------------------------

font = cv2.FONT_HERSHEY_SIMPLEX


id = 0
count = 0
names = ['Guest','윤현진', '임다솜', '김화정', '소희'] #김화정소희임다솜윤현진

try:

	print("sensor")
	time.sleep(0.5)
	print("Ready")
	
	

	while (True):
		if GPIO.input(PIR_PIN):
            
			playsound.playsound('camera.mp3')
			cam = cv2.VideoCapture(0)
			cam.set(3,640)
			cam.set(4,480)
			minW = 0.1*cam.get(3)
			minH = 0.1*cam.get(4)
			Hi = "Who"
			
			

			while True:
				ret, img = cam.read()
				img = cv2.flip(img, 1)
				gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
				faces = faceCascade.detectMultiScale(
					gray,
					scaleFactor = 1.2,
					minNeighbors = 5,
					minSize = (int(minW), int(minH)),
                    )

				
                

				for(x,y,w,h) in faces:
					cv2.rectangle(img, (x,y), (x+w,y+h), (0,255,0), 2)
					id, confidence = recognizer.predict(gray[y:y+h,x:x+w])
				#인식된 얼굴이 사용자 일때 (인식률 43%이상이면 사용자)

					if (confidence < 57):
						id = names[id]
						confidence = " {0}%".format(round(100 - confidence))
						cv2.putText(img, str(confidence), (x+5,y+h-5), font, 1, (255,255,0), 1)
						print ("Hi User!")
						Hi = "Hi"

						
						basename = id						
						suffix = datetime.datetime.now().strftime("%Y%m%d_%H%M%S") + '.jpeg'
						filename = "_".join([basename, suffix])
						
						path2 = '/home/colpop/image_store/'+ filename
						cv2.imshow('camera', img)
						cv2.imwrite(path2,img)
						fileUpload(filename)
						
						

						GPIO.output(door,GPIO.LOW)						
						time.sleep(1)						
						GPIO.output(door,GPIO.HIGH)
						#GPIO.cleanup(24)

						break
				#인식된 얼굴이 사용자가 아닐 때
					elif(confidence < 100 and confidence > 57):
						id = "Guest"
						count += 1
						#print round(100-confidence)
						print(count)
						if (count%33 == 0):
							print ('Who are you?')							
							playsound.playsound('notface.mp3')
							Hi = "Hi"
							
							basename2 = 'Guest'
							suffix = datetime.datetime.now().strftime("%Y%m%d_%H%M%S") + '.jpeg'
							filename = "_".join([basename2, suffix])		
							
							path2= '/home/colpop/image_store/'+filename
							cv2.imshow('camera', img)
							cv2.imwrite(path2,img)
							fileUpload(filename)
							

						break

				cv2.imshow('camera', img)
				if Hi == 'Hi':
					break

				k = cv2.waitKey(10) & 0xff
				if k == 27:
					break

			cam.release()
			cv2.destroyAllWindows()

			break
			#continue

		else:
			print("Not motion")
			time.sleep(0.05)

except KeyboardInterrupt:
	print("Quit")

