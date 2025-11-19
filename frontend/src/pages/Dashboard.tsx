import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import { logoWhite } from "@/assets";

import {
    Sidebar,
    SidebarContent,
    SidebarFooter,
    SidebarGroup,
    SidebarHeader,
} from "@/components/ui/sidebar"


export default function Dashboard() {
    return (

        <SidebarProvider>
            <div className="absolute top-3 left-3 z-20">                <SidebarTrigger /></div>
            <Sidebar>
                <SidebarHeader>
                    
                    <img src={logoWhite} alt="Immobiliaris Logo" className="h-8 w-auto mx-auto my-4" />
                </SidebarHeader>
                <SidebarContent>
                    <SidebarGroup>
                        <nav className="flex flex-col gap-2 mt-10">
                            <a href="#" className="px-3 py-2 rounded hover:bg-accent">Dashboard</a>
                            <a href="#" className="px-3 py-2 rounded hover:bg-accent">Proprietà</a>
                            <a href="#" className="px-3 py-2 rounded hover:bg-accent">Inquilini</a>
                            <a href="#" className="px-3 py-2 rounded hover:bg-accent">Contratti</a>
                            <a href="#" className="px-3 py-2 rounded hover:bg-accent">Pagamenti</a>
                        </nav>
                    </SidebarGroup>
                </SidebarContent>
                <SidebarFooter />
            </Sidebar>
            <main className="flex-1">
                {/* Main content goes here */}
            </main>
        </SidebarProvider>

    );
}