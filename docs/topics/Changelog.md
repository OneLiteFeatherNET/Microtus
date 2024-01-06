# Changelog

> The page may be outdated at times. Updates occur within an unspecified timeframe.
>
> To stay up-to-date with the latest releases, please
visit [this page](https://github.com/OneLiteFeatherNET/Microtus/releases)
> {style="warning"}

## Unreleased

## Version 1.1.4 {collapsible="true"}


## Version 1.1.3 - QOL Update {collapsible="true"}

#### 🐛 Fixes

- Update MinestomData dependecy usage [theEvilReaper](https://github.com/theEvilReaper) (#61)

#### ✨ Features

- Improve java keyword usage [theEvilReaper](https://github.com/theEvilReaper) (#70)
- Update version numbers for some dependencies [theEvilReaper](https://github.com/theEvilReaper) (#69)
- Add idea folder to the ignored file [theEvilReaper](https://github.com/theEvilReaper) (#68)
- Deprecate inventory constructors which take a string as parameter [theEvilReaper](https://github.com/theEvilReaper) (#66)
- Improve RegEx usage in the EncryptionResponsePacket [Joltras](https://github.com/Joltras) (#64)
- Update deprecation status from some methods [theEvilReaper](https://github.com/theEvilReaper) (#62)
- Add a section about how to update a branch [theEvilReaper](https://github.com/theEvilReaper) (#63)
- Remove jitpack integration [theEvilReaper](https://github.com/theEvilReaper) (#65)

#### 🧭 Changes

- Improve java keyword usage [theEvilReaper](https://github.com/theEvilReaper) (#70)
- Add idea folder to the ignore file [theEvilReaper](https://github.com/theEvilReaper) (#68)
- Improve RegEx usage in the EncryptionResponsePacket [Joltras](https://github.com/Joltras) (#64)
- Update deprecation status from some methods [theEvilReaper](https://github.com/theEvilReaper) (#62)
- Add a section about how to update a branch [theEvilReaper](https://github.com/theEvilReaper) (#63)

### 📝 Documentation updates

- Add a section about how to update a branch [theEvilReaper](https://github.com/theEvilReaper) (#63)

### 📦 Dependency updates

- Update actions/setup-java action to v4

## Version 1.1.2 - QOL Update {collapsible="true"}

### 💥 Breaking changes

- Update name convention of an ItemStack tag (internally) [theEvilReaper](https://github.com/theEvilReaper) (#52)

### 🐛 Fixes {id="fixes_3"}

- Add a pre-compiled pattern to reduce pattern creation [theEvilReaper](https://github.com/theEvilReaper) (#57)
- Update NBT handling for items wihout any NBT data [theEvilReaper](https://github.com/theEvilReaper) (#54)
- Improve entity position synchronisation [theEvilReaper](https://github.com/theEvilReaper) (#53)

### ✨ Features {id="features_3"}

- Add contribution file [theEvilReaper](https://github.com/theEvilReaper) (#56)
- Update name convention of an ItemStack tag (internally) [theEvilReaper](https://github.com/theEvilReaper) (#52)
- Bump dependencies [TheMeinerLP](https://github.com/TheMeinerLP) (#3)
- Update kyori blossom plugin [theEvilReaper](https://github.com/theEvilReaper) (#51)
- Update README file to include a section about jitpack [theEvilReaper](https://github.com/theEvilReaper) (#40)

### 🧭 Changes {id="changes_3"}

- Bump dependencies [TheMeinerLP](https://github.com/TheMeinerLP) (#3)

### ✅ Chore

- Cleanup patches and squash and sort some commits/patches together. [TheMeinerLP](https://github.com/TheMeinerLP) (#58)
- Update upstream [TheMeinerLP](https://github.com/TheMeinerLP) (#49)

### 📦 Dependency updates {id="dependency-updates_2"}

- Update dependency gradle to v8.4
- Update actions/checkout action to v4

## Version 1.1.1 - QOL Update {collapsible="true"}

### 🐛 Fixes {id="fixes_2"}

- Add missing telemetry variable to the advancement [theEvilReaper](https://github.com/theEvilReaper) (#30)
- Improve usage of a string pattern [theEvilReaper](https://github.com/theEvilReaper) (#28)
- Remove double registration of the ChatSessionUpdate packet [Joltras](https://github.com/Joltras) (#26)

### ✨ Features {id="features_2"}

- Update metadata implementation [theEvilReaper](https://github.com/theEvilReaper) (#31)
- Update entity metadata implementations [TheMeinerLP](https://github.com/TheMeinerLP) (#24)

### 🧭 Changes {id="changes_2"}

- Update metadata implementation [theEvilReaper](https://github.com/theEvilReaper) (#31)
- Improve usage of a string pattern [theEvilReaper](https://github.com/theEvilReaper) (#28)


## Version 1.1.0 - 1.20(.1) Update {collapsible="true"}

### 🚀 Major features

- Feature/1.20 support [TheMeinerLP](https://github.com/TheMeinerLP) (#16)

### 🐞 Major bug fixes

- Apply GameProfile to players when using Mojang Authenication [TheMeinerLP](https://github.com/TheMeinerLP) (#14)

### 🐛 Fixes {id="fixes_1"}

- Apply GameProfile to players when using Mojang Authenication [TheMeinerLP](https://github.com/TheMeinerLP) (#14)
- Fix issue Minestom#1883 [TheMeinerLP](https://github.com/TheMeinerLP) (#13)

### ✨ Features {id="features_1"}

- Feature/1.20 support [TheMeinerLP](https://github.com/TheMeinerLP) (#16)
- Implement hashcode and equals for some structures [theEvilReaper](https://github.com/theEvilReaper) (#23)
- Change license to have more afford [TheMeinerLP](https://github.com/TheMeinerLP) (#22)
- Add metaclasses for shield and banner [theEvilReaper](https://github.com/theEvilReaper) (#19)

### 🧭 Changes {id="changes_1"}

- Implement hashcode and equals for some structures [theEvilReaper](https://github.com/theEvilReaper) (#23)
- Change license to have more afford [TheMeinerLP](https://github.com/TheMeinerLP) (#22)
- Add metaclasses for shield and banner [theEvilReaper](https://github.com/theEvilReaper) (#19)

### 📦 Dependency updates {id="dependency-updates_1"}

- Update dependency gradle to v8.2

## Version 1.0.0 {collapsible="true"}

- Update to
  1.19.4 [#7](https://github.com/OneLiteFeatherNET/Microtus/pull/7) [TheMeinerLP](https://github.com/TheMeinerLP) -
  Breaking Changes
- Add missing InventoryCloseEvent call in the closeInventory
  method [#9](https://github.com/OneLiteFeatherNET/Microtus/pull/9) [TheMeinerLP](https://github.com/TheMeinerLP)
- Add 1.19.3 recipe
  categories [#5](https://github.com/OneLiteFeatherNET/Microtus/pull/5) [TheMeinerLP](https://github.com/TheMeinerLP) -
  Breaking Changes
- Fix team spam
  from [Minestom/Minestom#1864](https://github.com/Minestom/Minestom/issues/1864) [#4](https://github.com/OneLiteFeatherNET/Microtus/pull/4) [TheMeinerLP](https://github.com/TheMeinerLP)
- Add better differentiation of shift click. Add left or
  right [#2](https://github.com/OneLiteFeatherNET/Microtus/pull/2) [TheMeinerLP](https://github.com/TheMeinerLP) -
  Breaking Changes