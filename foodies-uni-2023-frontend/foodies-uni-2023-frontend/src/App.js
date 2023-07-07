import React from "react";
import {useRoutes} from "react-router-dom";
import ThemeProvider from "@mui/material/styles/ThemeProvider";
import DefaultTheme from "./themes/default-theme";
import {routes} from "./routes/routes";

import './App.css';

function App() {
    const appRoutes = useRoutes(routes);
    return (
        <ThemeProvider theme={DefaultTheme}>
            {appRoutes}
        </ThemeProvider>
    );
}

export default App;
