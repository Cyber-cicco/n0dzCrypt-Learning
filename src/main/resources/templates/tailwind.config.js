/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./**/*.{html,js}", "./index.html"],
  theme: {
    extend: {},
    colors: {
      primary : '#003452',
      secondary : '#ccecff',
      accent : '#fabb00',
      light : '#ffecb3',
      error : '#ea5353',
      warn : '#fdf7dd',
      warn2 : '#ca991c',
      grey : '#f2f2f2',
      dark : '#919191',
      black : '#000000',
      white : '#ffffff',
      header : '#f6f8f9'
    },
    fontFamily: {
      'sans': ['Ubuntu', 'sans-serif'],
      'serif': ['Ubuntu', 'serif'],
      'mono': ['Ubuntu', 'monospace'],
      'poppins': ['Poppins', 'sans-serif'],
    },
  },
  plugins: [],
}

