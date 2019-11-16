#!/usr/bin/env python3

# import standard lib
import os.path
import sys
from pdb import set_trace
import tkinter as tk
import tkinter.filedialog as fd
import PIL.ImageTk

# import custom lib
from CtTemp import get_CtTemp

class ChooseFileFrame(tk.Frame):
    def __init__(self, master, text):
        super().__init__(master)
        self.label = tk.Label(self, text=text)
        self.entry_filename = tk.Entry(self, width=80)
        self.btn_file = tk.Button(self, text='Choose file',
            command=lambda: self.choose_file(self.entry_filename))
        opts = {
            'padx':5,
            'pady':5,
        }
        
        self.label.pack(side=tk.LEFT, **opts) 
        self.entry_filename.pack(side=tk.LEFT, fill=tk.X, expand=1, **opts) 
        self.btn_file.pack(side=tk.TOP, expand = 1, **opts)
    
    def choose_file(self, entry):
        filetypes = (
            ("csv file", "*.csv"),
            ("Plain text files", "*.txt"),
            ("All files", "*"),
        )
        init_dir = os.path.expanduser('~/Downloads')
        
        filename = fd.askopenfilename(title='Open file',
            initialdir=init_dir, filetypes=filetypes)
        if filename:
            entry.delete(0, tk.END)
            entry.insert(0, filename)
            print(filename)

class CtTempUi(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("长投指数温度")
        opts = {
            'padx':5,
            'pady':10,
            'ipady':10,
        }

        self.choose_pe =  ChooseFileFrame(self, 'PE file')
        self.choose_pb =  ChooseFileFrame(self, 'PB file')
        
        self.lb_result = tk.Label(self, text='Result')
        self.entry_result = tk.Entry(self, width=80)
        self.btn_calc= tk.Button(self, text='Calc',
            command=self.calc)        

        self.choose_pe.pack()
        self.choose_pb.pack()
        self.lb_result.pack(side=tk.LEFT, **opts)
        self.entry_result.pack(side=tk.LEFT, fill=tk.X, expand=1, **opts)
        self.btn_calc.pack(expand=1, **opts)

    def calc(self):
        pb_csv = self.choose_pb.entry_filename.get()
        pe_csv = self.choose_pe.entry_filename.get()
        ct_temp =  get_CtTemp(pb_csv, pe_csv) 
        if not isinstance(ct_temp, str):
            ct_tmp *= 100
        self.entry_result.delete(0, tk.END)
        self.entry_result.insert(0, '{}'.format(ct_temp))

def main():
    app = CtTempUi()
    app.mainloop()
    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
