import logo from './logo192.png'

export default class URLMap {
  static mapTag(tag) {
    switch (tag) {
      case 'Plants':
        return logo
      case 'Space':
        return logo
      case 'Score':
        return logo
      default:
        return logo
    }
  }

  static mapResourceIcon(resource) {
    switch (resource) {
      case 'mc':
        return logo
      case 'steel':
        return logo
      case 'titanium':
        return logo
      case 'plants':
        return logo
      case 'energy':
        return logo
      case 'heat':
        return logo
      default:
        return logo
    }
  }
}