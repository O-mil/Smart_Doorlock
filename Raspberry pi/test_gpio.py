import RPi.GPIO as GPIO
import time

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(24, GPIO.OUT)

try:
    while 1:
        time.sleep(3)
        GPIO.output(24, 0)
        time.sleep(1)
        GPIO.output(24, 1)
        time.sleep(1)

finally:
    GPIO.cleanup()