#!/usr/bin/env python3

# import standard lib
import os
import sys

import msgbox

def ctx():
    global desktop
    global model
    global active_sheet

    desktop = XSCRIPTCONTEXT.getDesktop()
    model = desktop.getCurrentComponent()
    active_sheet = model.CurrentController.ActiveSheet

    #all three are pyuno
    print(f'desktop type: {type(desktop)}') 
    print(f'model type: {type(model)}')
    print(f'active_sheet type: {type(active_sheet)}')

def write_10():
    active_sheet.getCellByPosition(0, 0).String = "hello"
    for row in range(1,10):
        active_sheet.getCellByPosition(0, row).String = row * 2

def box_1(ctx):
    box = msgbox.MsgBox(ctx)
    box.addButton('OK')
    box.renderFromButtonSize()
    box.numberOflines = 2
    box.show('lixinghe', 0, "Watching")

def box_2(ctx):
    box = msgbox.MsgBox(ctx)
    box.addButton(r"Let's Go!")
    box.renderFromButtonSize(300)
    box.lineHeight = 100
    box.show('Ready?', 0, "Message")

def box_3(ctx):
    box = msgbox.MsgBox(ctx)
    box.addButton('Red')
    box.addButton('Green')
    box.addButton('Blue')
    box.show('What\'s your favourate color?', 0, "Question")
    print("Answer:", box.Response)

    box2 = msgbox.MsgBox(ctx)
    box2.addButton('OK')
    box2.show(box.Response, 0, "Answer")

def show_msg():
    #ctx = XSCRIPTCONTEXT.getComponentContex()
    ctx = XSCRIPTCONTEXT.getComponentContext()
    #box_1(ctx)
    #box_2(ctx)
    box_3(ctx)

def get_cell_type():
    selection = model.getCurrentSelection()
    print('model', type(model))
    area = selection.getRangeAddress()
    print('area', type(area), ' ', area)
    x = area.StartColumn
    y = area.StartRow
    print(f'{x},{y}')
    first_cell = active_sheet.getCellByPosition(x, y)
    print('first_cell', type(first_cell))
    cell_type = first_cell.Type
    print('cell_type:', cell_type)
    active_sheet.getCellByPosition(x+1, y).String = cell_type
    print(f'write to {x+1},{y}')

def read_save_data():
    sel = model.getCurrentSelection()
    area = sel.getRangeAddress() #CellRangeAddress

    #XCellRange
    s_left = area.StartColumn
    s_top = area.StartRow
    s_right = area.EndColumn
    s_bottom = area.EndRow
    range_source = active_sheet.getCellRangeByPosition(s_left,
            s_top,
            s_right,
            s_bottom)

    data_source = range_source.getDataArray()
#    print('area', type(area), ' ', area)
#    print('range_source', type(range_source), ' ', range_source)
#    print('data_source', type(data_source), ' ', data_source)

    offset = 2
    t_left = s_right + offset
    t_right = t_left + s_right - s_left
    t_top = s_top
    t_bottom =  s_bottom

    range_dst = active_sheet.getCellRangeByPosition(t_left,
            t_top,
            t_right,
            t_bottom)
    range_dst.setDataArray(data_source)

def reverse_ranges():
    #Return: index (left, top, right, bottom) of selection
    sel = model.getCurrentSelection()
    area = sel.getRangeAddress()
    print(f'sel type: {type(sel)}') #pyuno'ij
    print(f'area type: {type(area)}')   #ooo_script_framework.com.sun.star.table.CellRangeAddress 
    #(s_left, s_top, s_right, s_bottom)
    s_idx =  (area.StartColumn, area.StartRow,
                area.EndColumn, area.EndRow)
    s_range = active_sheet.getCellRangeByPosition(*s_idx)
    s_data = s_range.getDataArray()
    
    d_data = tuple(reversed(s_data))
    offset = 2
    print(s_idx)
    d_idx = (s_idx[2] + offset, 
            s_idx[1],
            s_idx[2] * 2 + offset - s_idx[0], 
            s_idx[3])
    d_range = active_sheet.getCellRangeByPosition(*d_idx)
    d_range.setDataArray(d_data)

def main(*args):
    ctx()
#    print("type：", type(args))
#    print("args:", args)
#    write_10()
#    show_msg()
#    get_cell_type()
    #read_save_data()
    reverse_ranges()
    print("Finish\n")
