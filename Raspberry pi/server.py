import socket
import time
import RPi.GPIO as GPIO

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
PIR_PIN = 17
GPIO.setup(PIR_PIN, GPIO.IN, pull_up_down = GPIO.PUD_DOWN)

door = 24
GPIO.setup(door, GPIO.OUT, initial = GPIO.HIGH)
input_prev=0

HOST = "0.0.0.0"
PORT = 3333
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print ('Socket created')
s.bind((HOST, PORT))
print ('Socket bind complete')
s.listen(1)

#파이 컨트롤 함수
def do_some_stuffs_with_input(input_string):
	#라즈베리파이를 컨트롤할 명령어 설정
    if input_string == "left":
        GPIO.output(door,GPIO.LOW)
        time.sleep(1)
        GPIO.output(door,GPIO.HIGH)
        #GPIO.cleanup(24)
        input_string = "open"
        

    else :
        input_string = input_string + " 없는 명령어 입니다."
    return input_string

while True:
	#접속 승인
    #s.listen(1)
    #print ('Socket now listening')
    conn, addr = s.accept()
    print("Connected by ", addr)

	#데이터 수신
    data = conn.recv(1024)
    data = data.decode("utf8").strip()
    if not data: break
    print("Received: " + data)

	#수신한 데이터로 파이를 컨트롤
    res = do_some_stuffs_with_input(data)
    print("Door :" + res)

	#클라이언트에게 답을 보냄
    conn.sendall(res.encode("utf-8"))
	#연결 닫기
    conn.close()
    #GPIO.cleanup(24)
    continue
s.close()
GPIO.close()