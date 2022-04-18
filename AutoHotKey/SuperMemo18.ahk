; main script for restricting execution of custom shortcut bindings to SuperMemo.
#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.
CoordMode, Mouse, Screen
#SingleInstance, force
SetTitleMatchMode,2

GroupAdd, SuperMemo, ahk_class TBrowser ; Browser
GroupAdd, SuperMemo, ahk_class TContents ; Content Window (Knowledge Tree)
GroupAdd, SuperMemo, ahk_class TElWind ; Element window
GroupAdd, SuperMemo, ahk_class TElDATAWind ; Element Data window
GroupAdd, SuperMemo, ahk_class TSMMain ; Toolbar

; highlight selected text
^H::
{
   send, ^{Enter}
   send, highlighter font
   send, {Enter}
}
Return
