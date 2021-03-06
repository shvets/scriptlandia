#! /usr/bin/env fan
//
// Copyright (c) 2008, Brian Frank and Andy Frank
// Licensed under the Academic Free License version 3.0
//
// History:
//   11 Apr 08  Brian Frank  Creation
//

using fand
using web
using webapp
using wisp

**
** Boot script for weblet hello world
**
class Boot : BootScript
{
  override Thread[] services :=
  [
    WispService.make("web")
    {
      port = 8080
      pipeline = [FindResourceStep {}, FindViewStep {}, ServiceViewStep {}].toImmutable
    }
  ]

  override Void setup()
  {
    Sys.ns.create(`/homePage`, Hello.make)
  }
}

@serializable
class Hello : Weblet
{
  override Void doGet()
  {
    res.headers["Content-Type"] = "text/plain"
    res.out.printLine("hello world #4")
  }
}
