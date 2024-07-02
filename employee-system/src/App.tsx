import './App.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Dashboard from "./components/dashboard.tsx";
import Check from "./components/check.tsx";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";

const queryClient = new QueryClient();

function App() {

    return (
        <QueryClientProvider client={queryClient}>
            <RouterProvider router={createBrowserRouter([
                {path: "", element: <Dashboard/>},
                {path: "/check", element: <Check/>},
            ])}/>
        </QueryClientProvider>
    )
}

export default App
