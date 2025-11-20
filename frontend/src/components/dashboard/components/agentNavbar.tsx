
import { logoWhite } from "@/assets";
import { Home, LayoutDashboard, Settings } from "lucide-react";
import { FaMoneyBillWave, FaRegQuestionCircle } from "react-icons/fa";

function AgentNavbar() {
    return (
        <nav
            className="fixed left-0 top-0 h-full w-13 md:w-60 bg-primary text-white flex flex-col"
            role="navigation"
            aria-label="Barra laterale principale"
        >
            <div className="flex items-center gap-3 border-b border-slate-800">
                <picture className="w-36 m-auto py-3 hidden md:block"><img src={logoWhite} alt="Immobiliaris" /></picture>
            </div>

            <ul className="flex-col text-black">
                <li>
                    <a
                        href="#"
                        className="flex items-center bg-secondary gap-3 px-4 py-4 hover:bg-card transition-all focus:bg-card">
                        <LayoutDashboard className="w-5 h-5" />
                        <span className="hidden md:block">Dashboard</span>
                    </a>
                </li>
                <li>
                    <a
                        href="#"
                        className="flex items-center bg-secondary gap-3 px-4 py-4 hover:bg-card transition-all focus:bg-card">
                        <FaRegQuestionCircle className="w-5 h-5" />
                        <span className="hidden md:block">Richieste in attesa</span>
                    </a>
                </li>
                <li>
                    <a
                        href="#"
                        className="flex items-center bg-secondary gap-3 px-4 py-4 hover:bg-card transition-all focus:bg-card">
                        <Home className="w-5 h-5" />
                        <span className="hidden md:block">Immobili</span>
                    </a>
                </li>
                <li>
                    <a
                        href="#"
                        className="flex items-center bg-secondary gap-3 px-4 py-4 hover:bg-card transition-all focus:bg-card">
                        <FaMoneyBillWave className="w-5 h-5" />
                        <span className="hidden md:block">Acquisizioni</span>
                    </a>
                </li>
                <li>
                    <a
                        href="#"
                        className="flex items-center bg-secondary gap-3 px-4 py-4 hover:bg-card transition-all focus:bg-card">
                        <Settings className="w-5 h-5" />
                        <span className="hidden md:block">Impostazioni</span>
                    </a>
                </li>
            </ul>
        </nav>
    );
}
export default AgentNavbar;