import {createTheme} from "@mui/material";

/*
colors
------
main-green: #83a31a
main-yellow: #FFDC2A
secondary-green: #b9db2f
main-gray: #edf2ff

*/

const DefaultTheme = createTheme({
    palette: {
        primary: {
            main: '#4caf50',
        },
        secondary: {
            main: '#FFDC2A',
        },
        btnColor: {
            white: '#b9db2f',
            black: '#83a31a',
        },
        subText: {
            main: '#b9db2f'
        }
    },
    typography: {
        "fontFamily": `"Montserrat", "Helvetica", "Arial", sans-serif`,
        "fontSize": 14,
        "fontWeightLight": 300,
        "fontWeightRegular": 400,
        "fontWeightMedium": 500,
        button: {
            textTransform: 'none'
        },
        AppBar: {}
    },
    components: {
        MuiTextField: {
            styleOverrides: {
                colorPrimary: {
                    backgroundColor: '#edf2ff',
                }
            }
        }
    }
});

export default DefaultTheme;
