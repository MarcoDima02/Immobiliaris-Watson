import { HomeIcon } from "lucide-react";

function AgentCard({
    agentName,
    agentDescription,
    agentImage,
    soldProperties
}: {
    agentName: string;
    agentDescription: string;
    agentImage: string;
    soldProperties: number;
}) {
    return (
        <div className='bg-white w-full rounded-[50px] flex flex-col border-16 border-white shadow-2xl'>
            <div>
                <img src={agentImage} alt={agentName} className='h-full w-full rounded-[30px]' />
            </div>
            <div className='px-4'>
                <h3 className='text-xl font-bold mt-5 text-black'>{agentName}</h3>
                <h4 className='text-light-grey font-medium text-md'>{agentDescription}</h4>
                <div className='flex gap-2 items-center justify-center mt-18'>
                    <HomeIcon className='text-secondary h-5' />
                    <p className='text-secondary font-semibold text-center text-sm'>
                        {soldProperties}+ immobili venduti
                    </p>
                </div>
            </div>
        </div>
    );
}

export default AgentCard;
