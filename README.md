# mvc-multi-context
mvc multi project on spring boot

3 context path :
- /api
- /api/one
- /api/two


POST /api/two => 404

UrlPathHelper#getLookupPathForRequest : line 173
```
if (!"".equals(rest)) {
  return rest;
}
```

why not 
```
if (notEmpty(rest)) {
  return rest;
}
```
