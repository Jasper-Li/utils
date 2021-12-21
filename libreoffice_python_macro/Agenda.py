#!/usr/bin/env python3
'''Precondition:
1. You must open APSO console'''

# import standard lib
import os
import sys
import unittest
from unittest import TestCase, skip
from typing import NamedTuple
from dataclasses import dataclass

#global section
desktop = XSCRIPTCONTEXT.getDesktop()
model = desktop.getCurrentComponent()
active_sheet = model.CurrentController.ActiveSheet

def show_msg(text):
    import msgbox
    myBox = msgbox.MsgBox(XSCRIPTCONTEXT.getComponentContext())
    myBox.addButton('OK')
    myBox.renderFromBoxSize(250)
    myBox.show(text,0,"TestFailure")

@dataclass
class CellAddress:
    sheet_idx: int
    column:int
    row: int

class Cell:
    def __init__(self, cell):
        self.cell = cell

    def name(self):
        buf = self.cell.AbsoluteName.split('.')[-1]
        return ''.join(buf.split('$'))

    def address(self) -> CellAddress:
        add = self.cell.getCellAddress()
        add = CellAddress(add.Sheet, add.Column, add.Row)
        #print(add)
        return add
    def type(self):
        return self.cell.getType().value
    def value(self):
        type = self.type()
        if type == 'TEXT':
            return self.cell.getString()
        elif type == 'VALUE':
            return self.cell.getValue()
        elif type == 'FORMULA':
            return self.cell.getFormula()
    def __repr__(self) -> str:
        return f'{{{self.name()}, {self.type()}, {self.value()}}}'

    def print(self):
        c = self.cell
        type = c.getType()
        print(f"position: {self.address()}\n\
        type: {self.type()}\n\
        value: {self.value()}")

def search_text(text, sheet=active_sheet, only_once=True) -> Cell:
    '''return XCell'''
    print("debug: search at file:", sheet.getName())
    s_discriptor = sheet.createSearchDescriptor()
    s_discriptor.setSearchString(text)

    #XIndexAccess: container of cellrange
    results = sheet.findAll(s_discriptor) 
    count = results.getCount()
    if only_once:
        assert count == 1, f"It should come only once, get {count}"
    
    e = results.getByIndex(0) # XCellRange
    cell = e.getCellByPosition(0,0) #XCell
    return Cell(cell)
    #print(cell.name())
    #cells = results.getCells() # XSheetCellRanges .getCells
    #print(2, cells)
    # XEnumerationAccess: container of cell
    #for cell in cells:
class Row:
    '''core data is (XCell, XCell)'''
    def __init__(self, cell_address: CellAddress, horizonal_offset):
        pass
        #cells = range.getCells()
        #print(cells[0])
        
def get_row_data(cell_address: CellAddress, horizonal_offset):
    '''
    Return (str, str)
    '''
    #print(cell_address)
    range = active_sheet.getCellRangeByPosition(
        cell_address.column,
        cell_address.row,
        cell_address.column + horizonal_offset,
        cell_address.row
    )
    
    data = range.getDataArray()
    return data[0]

def get_valid_roles() -> tuple:
    '''from config'''
    return (
        ("Warmer", "TMD", "TTM", "GE", "Timer", "Ah Counter", "Grammarian", "Sharing Master", 
        "Speaker 1", "Speaker 2", "Speaker 3", "Speaker 4", "Speaker 5", 
        "Evaluator 1", "Evaluator 2", "Evaluator 3", "Evaluator 4", "Evaluator 5", 
        "TTE", "Photographer", "Poster", "Meeting Manager", 
        ),
        ("暖场官", "主持人", "即兴主持人", "总评", "时间官", "哼哈官", "语法官", "分享嘉宾",
        "演讲人1", "演讲人2", "演讲人3", "演讲人4", "演讲人5", 
        "个评1", "个评2", "个评3", "个评4", "个评5", 
        "即兴评估", "摄影师", "海报", "会议经理"
        ),
    )
    # TODO read from config
    #sheets = model.Sheets
    ##print(sheets)
    #hit = None
    #for sheet in sheets:
    #    if sheet.getName() == 'config':
    #        hit = sheet
    #if not hit:
    #    raise Exception("Failed to find 'config' sheet")
    #start = search_text('Role', sheet=hit)
    #end = search_text('Role_End', sheet=hit)
    #range = active_sheet.getCellRangeByPosition(
    #    start.column +1,
    #    start.row + 1,
    #    start.column,
    #    start.row
    #)
    
    #data = range.getDataArray()
    #return ''

@dataclass
class Person:
    role: str
    name: str
    level: str
    club: str
    into: str
    
    valid_roles = get_valid_roles()
    invaid_roles = ('Role', '')
    def __init__(self, role, name='', level='', club='', into=''):
        self.role = role
        self.name = name
        self.level = level
        self.club = club
        self.into = into

    def is_valid_role(self) -> bool:
        role = self.role
        if role in self.__class__.invaid_roles:
            return  False
        for check in self.__class__.valid_roles:
            if role in check:
                return True
        msg = f"'{role}'' isn't a valid role in 'config' sheet."
        show_msg(msg)

def main(*args):
    #TODO
    print("Agenda Finished")

class AgendaTestCase(TestCase):
    @skip('dev') 
    def test_cell(self):
        cell = Cell(active_sheet.getCellByPosition(0,0))
        self.assertEqual(cell.name(), "A1")
        cell = search_text('Role')
        self.assertEqual(cell.name(), 'D2')
        self.assertEqual(cell.address(), CellAddress(0, 3, 1))
        self.assertEqual(cell.type(), 'TEXT')
        self.assertEqual(cell.value(), 'Role')
        self.assertEqual(str(cell), '{D2, TEXT, Role}')
        cell.print()
        anchor = cell.address()
        #print(anchor)
        row = get_row_data(anchor, 4)
        self.assertEqual(row, ('Role', 'Name', 'Level', 'Club', 'One Sentence Introduction'))
        print(row)
        anchor.row += 1
        row = get_row_data(anchor, 4)
        print(row)

    def test_Person(self):
        roles = (
            (False, ('Role', )),
            (False, ('', )),
         )
        for status, data in roles:
            person = Person(*data)
            self.assertEqual(person.is_valid_role(), status, f"Failed to test {data}")

        # it will post msgbox
        #data = ('lixinghe',)
        #person = Person(*data)
        #status = person.is_valid_role()
        
def test_main(*args):
    # print(sys.path)
    suite = unittest.TestSuite()
    suite.addTests(unittest.TestLoader().loadTestsFromTestCase(AgendaTestCase))
    runner = unittest.TextTestRunner()
    result = runner.run(suite)
    # print('000', result)
    f = result.failures
    if len(f) != 0:
        for test,reason in f:
            show_msg(reason)
            break