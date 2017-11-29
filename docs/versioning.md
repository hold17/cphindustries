# Semantic Versioning

## Version name

Pretty simple. It is the userfriendly name, i.e. (v1.7.4-alpha.5)

## Version code

An incremental integer value that represents the version of the application code. Read [Official Documentation](https://developer.android.com/google/play/publishing/multiple-apks.html#VersionCodes) for more details.

**Example**:
```
Version name: v3.1.0
Version code: (with API level 23) 230030100
```

**Description**:
```
04 = min api level
0 = screen sizes or gl texture formats
03 = major version 
01 = minor version
00 = patch version
```

## Automating versioning

Versioning can be automated with gradle. Read [this Medium article](https://medium.com/@maxirosson/versioning-android-apps-d6ec171cfd82) for more information on how to do this.

## Links
- Version code & version name: <https://medium.com/@maxirosson/versioning-android-apps-d6ec171cfd82>
- Version code official doc: <https://developer.android.com/google/play/publishing/multiple-apks.html#VersionCodes>
- Semantic versioning: <https://semver.org/>
