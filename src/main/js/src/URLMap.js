const logo = '/logo192.png'
const plants = '/Plants-bonus.png'
const steel = '/Steel-bonus.png'
const titanium = '/Titanium-bonus.png'

export default class URLMap {
  static mapTag(tag) {
    switch (tag) {
      case 'Plants':
        return plants
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
        return steel
      case 'titanium':
        return titanium
      case 'plants':
        return plants
      case 'energy':
        return logo
      case 'heat':
        return logo
      default:
        return logo
    }
  }
}