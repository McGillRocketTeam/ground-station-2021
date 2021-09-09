import tkinter as tk
from Main import main


# Method for setting the number of simulations and launching the Main.py file
def launchButtonClick():
    # Calls Main.py
    if entry.get() == '':
        print("Invalid input")
    else:
        print("Calling Main.py ")
        main(int(entry.get()))


# Beginning of the root object
root = tk.Tk()

HEIGHT = 500
WIDTH = 700

root.title('Rocket Team UI')

# The canvas onto which we will be working on
canvas = tk.Canvas(root, height=HEIGHT, width=WIDTH)
canvas.pack()

# Background Design
frame = tk.Frame(root, bg='#363738')   # Dark Blue 05324F  | Grey 2F3842 | Grey Fitting MRT Logo 363738 | Space 011121
frame.place(relwidth=1, relheight=1)

# McGill Rocket Team Label
title = tk.Label(frame, text="McGill Rocket Team Simulator", anchor="center", background="#363738", foreground= "#B81A14")
title.config(font=("Impact", 40))
title.pack()

# Enter simulations number Label
simLabel = tk.Label(frame, text='Number of Simulations', background="#363738", foreground="black", anchor="center")
simLabel.config(font=("Impact", 20))
simLabel.pack()

# User Input
entry = tk.Entry(frame, background='#363738', foreground="white")
simLabel.config(font=("Impact", 20))
entry.pack()

# Launch Button
launchButton = tk.Button(frame, text='LAUNCH', background='#363738', foreground="#B81A14", command=launchButtonClick, relief='groove')
launchButton.config(font=("Impact", 20))
launchButton.place(relx=0.425, rely=0.3)

root.mainloop()











# import tkinter as tk
#
# def onKeyPress(event):
#     text.insert('end', 'You pressed %s\n' % (event.char, ))
#
# root = tk.Tk()
# root.geometry('300x200')
# text = tk.Text(root, background='black', foreground='white', font=('Comic Sans MS', 12))
# text.pack()
# root.bind('<KeyPress>', onKeyPress)
# root.mainloop()


#For resizing background image with window size
# from tkinter import *
#
# from PIL import Image, ImageTk
#
# root = Tk()
# root.title("Title")
# root.geometry("600x600")
# root.configure(background="black")
#
#
# class Example(Frame):
#     def __init__(self, master, *pargs):
#         Frame.__init__(self, master, *pargs)
#
#
#
#         self.image = Image.open("bgg.png")
#         self.img_copy= self.image.copy()
#
#
#         self.background_image = ImageTk.PhotoImage(self.image)
#
#         self.background = Label(self, image=self.background_image)
#         self.background.pack(fill=BOTH, expand=YES)
#         self.background.bind('<Configure>', self._resize_image)
#
#     def _resize_image(self,event):
#
#         new_width = event.width
#         new_height = event.height
#
#         self.image = self.img_copy.resize((new_width, new_height))
#
#         self.background_image = ImageTk.PhotoImage(self.image)
#         self.background.configure(image =  self.background_image)
#
#
#
# e = Example(root)
# e.pack(fill=BOTH, expand=YES)

#
# root.mainloop()

