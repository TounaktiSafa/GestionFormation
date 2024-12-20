/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.html",
    "./src/**/*.{html,js}", // Assurez-vous que cela couvre le chemin de votre fichier HTML
    "./*.html",// Pour les fichiers Thymeleaf HTML
   ],
  theme: {
    extend: {
      colors: {
        customRed: '#C96868',
        shadowcolor : '#d07b7b',
        beigeclair : '#FADFA1',
        bleuvert : '#7EACB5'   // Define a custom color with a name
      },
    },
  },
  plugins: [],
}

