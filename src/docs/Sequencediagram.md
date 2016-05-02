[Link Source](https://www.websequencediagrams.com/?lz=dGl0bGUgU2hvcnRlbmVyCgpDbGllbnQtPgAKCTogUE9TVCBodHRwOi8vcG9nLm5pbmphL3MAKwlub3RlIHJpZ2h0IG9mIAA7BgogICAgewABBXVybDoANwh6ZWxkYS5jb20AGwVjdXN0b21OYW1lOiBiZW1vYmkAMgV9CmVuZCBub3RlCgoKCgpvcHQAHwsgPT0gbnVsbABcBQCBLwkAgSMNdmFsaWRhdGVEYXRhKCkAgQUFbG9vcCBnZW5lcmF0ZU5leEhhc2gAMhBEYXRhYmFzZTogY2hlY2tJZkV4aXN0ADoHABUIAIIMDVJldHVybnMgb2sgKGJyZWFrKQplbmQKCmVuZAoKAEUVQwBODG5hbWUpABEXYWxscyBzZXQodXJsLCBuYW1lLCB2aWV3cykKAGwfAG0MAINHBgARDQCDKAZsZWYAgygFAINvCgCDJgphbGlhczogAIMmBSwAgzgKIgCDcREAgy0GAB0Gc3RhdGlzdGljczoAg3AHICAgIHRpbWU6IDEwbXMgfQCDShEAgRUTSFRUUCAyMDA6IENyZWF0ZWQKAIR8FUdFAIRUPG4AhFMdAIJ0G2dlAIMsCACCUDQAgVIKAIJPN3RhcmdldFUAgnAcAIZxBQCEIgU6IDAAhjoMAIdCFFUAhn9WAIJCHQCFUhtmaW5kQnlOYW1lAIYSBwCGVh0AiSkJIE9iamVjdACFdVIAhg0ccmVzb3VyY2UgdXBkYXRlZCBzdWNjZXNzZnVsbHkAhTsoACceAIp1FURFTEVURQCFJFgAgih1ZGVsZXQAg2sIAIJPJQAsBgCCWxkAgzchAIJvJgBRFQCIZCkAjUcHAIVTHGV0Y2gAi2EiAIRbBigAi1ckAI8bB2V4YW1wbGUAjmAFAIpLGDMwMjogUmVkaXJlY3RzIHRvAI9aBQCPExIK&s=napkin)
```
title Shortener

Client->Shortener: POST http://pog.ninja/shortener
note right of Client
    {
    url: http://zelda.com
    customName: bemobi
    }
end note




opt customName == null
    Shortener->Shortener: validateData()
    loop generateNexHash
    Shortener->Database: checkIfExist()
    Database->Shortener: Returns ok (break)
end

end

Shortener->Database: CheckIfExist(name)
Shortener->Database: Calls set(url, name, views)
Database->Shortener: Returns ok

Shortener->Client: HTTP 201: Created
note left of Shortener
    {
    alias: zelda,
    url: "http://pog.ninja/bemobi,
    statistics: {
        time: 10ms }
    }
end note




Client->Shortener: GET http://pog.ninja/shortener
note right of Client
    {
    name: bemobi
    }
end note


Shortener->Database: Calls get(name)

Database->Shortener: Returns ok
Shortener->Client: HTTP 200: ok

note left of Shortener
    {
    alias: zelda,
    targetUrl: "http://pog.ninja/bemobi
    views: 0
end note


Client->Shortener: PUT http://pog.ninja/shortener
note right of Client
    {
    url: http://zelda.com
    name: bemobi
    }
end note

Shortener->Database: Calls findByName(name)
Database->Shortener: Returns Shortener Object
Shortener->Database: Calls set(url, name, views)
Database->Shortener: Returns ok

note left of Shortener
    resource updated successfully
end note

Shortener->Client: HTTP 200: resource updated successfully


Client->Shortener: DELETE http://pog.ninja/shortener
note right of Client
    {
    name: bemobi
    }
end note

Shortener->Database: Calls findByName(name)
Database->Shortener: Returns Shortener Object
Shortener->Database: Calls delete(name)

note left of Shortener
    resource deleted successfully
end note

Database->Shortener: Returns ok

Shortener->Client: HTTP 200: resource deleted successfully

Client->Shortener: GET http://pog.ninja/bemobi
Shortener->Database: Calls fetch(name)
Shortener->Database: Calls update(views)
Database->Shortener: Returns http://example.com
Shortener->Client: HTTP 302: Redirects to http http://zelda.com





```