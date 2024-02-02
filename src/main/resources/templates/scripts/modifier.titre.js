function changeToModifier() {

    let titreModif = document.querySelector(`#titre-modif`)
    let titre = document.querySelector(`#titre`)

    if(titreModif.classList.contains('hidden')) {
        titreModif.classList.remove('hidden')
        titre.classList.add('hidden')
        const titreInput = titreModif.querySelector('#titre')
        titreInput.focus();
        titreInput.select();
        return
    }

    titreModif.classList.add('hidden')
    titre.classList.remove('hidden')
}
