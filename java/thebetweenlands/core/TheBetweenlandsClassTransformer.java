package thebetweenlands.core;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.tree.AbstractInsnNode.*;

import java.util.Iterator;
import java.util.ListIterator;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class TheBetweenlandsClassTransformer implements IClassTransformer {
	public static final String SLEEP_PER_TICK = "sleepPerTick";

	private static final String BL_FORGE_HOOKS = "thebetweenlands/forgeevent/BLForgeHooks";

	private static final String BL_FORGE_HOOKS_CLIENT = "thebetweenlands/forgeevent/BLForgeHooksClient";

	private static final String DEBUG_HANDLER_CLIENT = "thebetweenlands/event/debugging/DebugHandlerClient";

	private static final String PERSPECTIVE = "thebetweenlands/client/perspective/Perspective";

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes) {
		boolean obf = false;
		if ("net.minecraft.server.MinecraftServer".equals(name)) {
			return writeClass(transformMinecraftServer(readClass(classBytes)));
		} else if ((obf = "yz".equals(name)) || "net.minecraft.entity.player.EntityPlayer".equals(name)) {
			return writeClass(transformEntityPlayer(readClass(classBytes), obf));
		} else if ((obf = "sv".equals(name)) || "net.minecraft.entity.EntityLivingBase".equals(name)) {
			return writeClass(transformEntityLivingBase(readClass(classBytes), obf));
		} else if ((obf = "bao".equals(name)) || "net.minecraft.client.Minecraft".equals(name)) {
			return writeClass(transformMinecraft(readClass(classBytes), obf));
		} else if ((obf = "bdw".equals(name)) || "net.minecraft.client.gui.GuiScreen".equals(name)) {
			return writeClass(transformGuiScreen(readClass(classBytes), obf));
		} else if ((obf = "oi".equals(name)) || "net.minecraft.server.management.ServerConfigurationManager".equals(name)) {
			return writeClass(transformServerConfigurationManager(readClass(classBytes), obf));
		} else if ((obf = "baj".equals(name)) || "net.minecraft.client.renderer.ActiveRenderInfo".equals(name)) {
			return writeClass(transformActiveRenderInfo(readClass(classBytes), obf));
		} else if ((obf = "blt".equals(name)) || "net.minecraft.client.renderer.EntityRenderer".equals(name)) {
			return writeClass(transformEntityRenderer(readClass(classBytes), obf));
		} else if ((obf = "bnn".equals(name)) || "net.minecraft.client.renderer.entity.RenderManager".equals(name)) {
			return writeClass(transformRenderManager(readClass(classBytes), obf));
		} else if ((obf = "sa".equals(name)) || "net.minecraft.entity.Entity".equals(name)) {
			return writeClass(transformEntity(readClass(classBytes), obf));
		}
		return classBytes;
	}

	private ClassNode readClass(byte[] classBytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(classBytes);
		classReader.accept(classNode, 0);
		return classNode;
	}

	private byte[] writeClass(ClassNode classNode) {
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(classWriter);
		return classWriter.toByteArray();
	}

	private ClassNode transformMinecraftServer(ClassNode classNode) {
		FieldNode sleepPerTickField = new FieldNode(ACC_PUBLIC, SLEEP_PER_TICK, "J", null, null);
		classNode.fields.add(sleepPerTickField);
		boolean needsRun = true, needsInit = true;
		for (MethodNode method : classNode.methods) {
			if (needsInit && "<init>".equals(method.name)) {
				needsInit = false;
				for (int i = 0; i < method.instructions.size(); i++) {
					AbstractInsnNode insnNode = method.instructions.get(i);
					if (insnNode.getOpcode() == RETURN) {
						InsnList initSleepPerTickInsns = new InsnList();
						initSleepPerTickInsns.add(new VarInsnNode(ALOAD, 0));
						initSleepPerTickInsns.add(new LdcInsnNode(50L));
						initSleepPerTickInsns.add(new FieldInsnNode(PUTFIELD, "net/minecraft/server/MinecraftServer", SLEEP_PER_TICK, "J"));
						method.instructions.insertBefore(insnNode, initSleepPerTickInsns);
						break;
					}
				}
			}
			if (needsRun && "run".equals(method.name)) {
				needsRun = false;
				Long fifty = Long.valueOf(50);
				for (int i = 0; i < method.instructions.size(); i++) {
					AbstractInsnNode insnNode = method.instructions.get(i);
					if (insnNode.getType() == LDC_INSN && fifty.equals(((LdcInsnNode) insnNode).cst)) {
						InsnList accessSleepPerTickInsns = new InsnList();
						accessSleepPerTickInsns.add(new VarInsnNode(ALOAD, 0));
						accessSleepPerTickInsns.add(new FieldInsnNode(GETFIELD, "net/minecraft/server/MinecraftServer", SLEEP_PER_TICK, "J"));
						method.instructions.insert(insnNode, accessSleepPerTickInsns);
						method.instructions.remove(insnNode);
						break;
					}
				}
			}
			if (!needsRun && !needsInit) {
				break;
			}
		}
		return classNode;
	}

	private ClassNode transformEntityPlayer(ClassNode classNode, boolean obf) {
		String getHurtSoundName = obf ? "aT" : "getHurtSound";
		String entityPlayerClass = obf ? "yz" : "net/minecraft/entity/player/EntityPlayer";
		for (MethodNode method : classNode.methods) {
			if (getHurtSoundName.equals(method.name) && "()Ljava/lang/String;".equals(method.desc)) {
				InsnList insns = method.instructions;
				insns.clear();
				insns.add(new VarInsnNode(ALOAD, 0));
				insns.add(new MethodInsnNode(INVOKESTATIC, BL_FORGE_HOOKS, "onPlayerGetHurtSound", String.format("(L%s;)Ljava/lang/String;", entityPlayerClass), false));
				insns.add(new InsnNode(ARETURN));
				break;
			}
		}
		return classNode;
	}

	private ClassNode transformEntityLivingBase(ClassNode classNode, boolean obf) {
		String setAttackTargetName = obf ? "b" : "setRevengeTarget";
		String entityLivingBaseClass = obf ? "sv" : "net/minecraft/entity/EntityLivingBase";
		String setAttackTargetDescription = String.format("(L%s;)V", entityLivingBaseClass);
		for (MethodNode method : classNode.methods) {
			if (setAttackTargetName.equals(method.name) && setAttackTargetDescription.equals(method.desc)) {
				for (int i = 0; i < method.instructions.size(); i++) {
					AbstractInsnNode insnNode = method.instructions.get(i);
					if (insnNode.getType() == METHOD_INSN) {
						MethodInsnNode methodNode = (MethodInsnNode) insnNode;
						methodNode.owner = BL_FORGE_HOOKS;
						methodNode.name = "onLivingSetRevengeTarget";
						break;
					}
				}
				break;
			}
		}
		return classNode;
	}

	private ClassNode transformMinecraft(ClassNode classNode, boolean obf) {
		String startGame = obf ? "ag" : "startGame";
		String runGameLoop = obf ? "ak" : "runGameLoop";
		String thirdPersonView = obf ? "aw" : "thirdPersonView";
		String runTick = obf ? "p" : "runTick";
		String isPressed = obf ? "f" : "isPressed";
		String handlePlayerAttackInput = obf ? "al" : "func_147116_af";
		String minecraft = obf ? "bao" : "net/minecraft/client/Minecraft";
		String leftClickCounter = obf ? "U" : "leftClickCounter";
		boolean needsStartGame = true;
		boolean needsRunGameLoop = true;
		boolean needsRunTick = true;
		boolean needsHandlePlayerAttackInput = true;
		for (MethodNode method : classNode.methods) {
			if (needsStartGame && startGame.equals(method.name) && "()V".equals(method.desc)) {
				for (int i = method.instructions.size() - 1; i >= 0; i--) {
					AbstractInsnNode insnNode = method.instructions.get(i);
					if (insnNode.getOpcode() == RETURN) {
						method.instructions.insertBefore(insnNode, new MethodInsnNode(INVOKESTATIC, DEBUG_HANDLER_CLIENT, "onMinecraftFinishedStarting", "()V", false));
						break;
					}
				}
				needsStartGame = false;
			}
			if (needsRunGameLoop && runGameLoop.equals(method.name) && "()V".equals(method.desc)) {
				for (int i = method.instructions.size() - 1; i >= 0; i--) {
					AbstractInsnNode insnNode = method.instructions.get(i);
					if (insnNode.getOpcode() == PUTFIELD && thirdPersonView.equals(((FieldInsnNode) insnNode).name)) {
						method.instructions.set(insnNode.getPrevious(), new MethodInsnNode(INVOKESTATIC, PERSPECTIVE, "getInsideOpaqueBlockView", "()I", false));
						break;
					}
				}
				needsRunGameLoop = false;
			}
			if (needsRunTick && runTick.equals(method.name) && "()V".equals(method.desc)) {
				for (int i = 0; i < method.instructions.size(); i++) {
					AbstractInsnNode insnNode = method.instructions.get(i);
					if (insnNode.getOpcode() == INVOKEVIRTUAL && isPressed.equals(((MethodInsnNode) insnNode).name)) {
						Iterator<AbstractInsnNode> insns = method.instructions.iterator(i + 6);
						while (insns.hasNext()) {
							insnNode = insns.next();
							if (insnNode.getOpcode() == ICONST_0) {
								method.instructions.set(insnNode, new MethodInsnNode(INVOKESTATIC, PERSPECTIVE, "cyclePerspective", "()I", false));
								break;
							}
							insns.remove();
						}
						break;
					}
				}
				needsRunTick = false;
			}
			if (needsHandlePlayerAttackInput && handlePlayerAttackInput.equals(method.name) && "()V".equals(method.desc)) {
				method.localVariables.clear();
				method.instructions.clear();
				method.instructions.add(new VarInsnNode(ALOAD, 0));
				method.instructions.add(new InsnNode(DUP));
				method.instructions.add(new FieldInsnNode(GETFIELD, minecraft, leftClickCounter, "I"));
				method.instructions.add(new MethodInsnNode(INVOKESTATIC, BL_FORGE_HOOKS_CLIENT, "handlePlayerAttackInput", "(I)I", false));
				method.instructions.add(new FieldInsnNode(PUTFIELD, minecraft, leftClickCounter, "I"));
				method.instructions.add(new InsnNode(RETURN));
				needsHandlePlayerAttackInput = false;
			}
			if (!needsRunGameLoop && !needsStartGame && !needsRunTick && !needsHandlePlayerAttackInput) {
				break;
			}
		}
		return classNode;
	}

	private ClassNode transformGuiScreen(ClassNode classNode, boolean obf) {
		String handleInputName = obf ? "p" : "handleInput";
		String handleKeyboardInputName = obf ? "l" : "handleKeyboardInput"; 
		for (MethodNode method : classNode.methods) {
			if (handleInputName.equals(method.name) && "()V".equals(method.desc)) {
				for (int i = 0; i < method.instructions.size(); i++) {
					AbstractInsnNode insnNode = method.instructions.get(i);
					if (insnNode.getOpcode() == INVOKEVIRTUAL) {
						MethodInsnNode methodInsnNode = (MethodInsnNode) insnNode;
						if (handleKeyboardInputName.equals(methodInsnNode.name) && "()V".equals(methodInsnNode.desc)) {
							InsnList insns = new InsnList();
							insns.add(new FieldInsnNode(GETSTATIC, DEBUG_HANDLER_CLIENT, "INSTANCE", 'L'+ DEBUG_HANDLER_CLIENT + ';'));
							insns.add(new InsnNode(ACONST_NULL));
							insns.add(new MethodInsnNode(INVOKEVIRTUAL, DEBUG_HANDLER_CLIENT, "onKeyInput", "(Lcpw/mods/fml/common/gameevent/InputEvent$KeyInputEvent;)V", false));
							method.instructions.insert(insnNode, insns);
							break;
						}
					}
				}
				break;
			}
		}
		return classNode;
	}

	private ClassNode transformServerConfigurationManager(ClassNode classNode, boolean obf) {
		String createPlayerForUserName = obf ? "f" : "createPlayerForUser";
		String entityPlayerMPClass = obf ? "mw" : "net/minecraft/entity/player/EntityPlayerMP";
		String createPlayerForUserDescription = String.format("(Lcom/mojang/authlib/GameProfile;)L%s;", entityPlayerMPClass);
		String serverConfigurationManagerClass = obf ? "oi" : "net/minecraft/server/management/ServerConfigurationManager";
		for (MethodNode method : classNode.methods) {
			if (createPlayerForUserName.equals(method.name) && createPlayerForUserDescription.equals(method.desc)) {
				method.instructions.clear();
				method.localVariables.clear();
				method.instructions.add(new VarInsnNode(ALOAD, 0));
				method.instructions.add(new VarInsnNode(ALOAD, 1));
				method.instructions.add(new MethodInsnNode(INVOKESTATIC, BL_FORGE_HOOKS, "createPlayerForUser", String.format("(L%s;Lcom/mojang/authlib/GameProfile;)L%s;", serverConfigurationManagerClass, entityPlayerMPClass), false));
				method.instructions.add(new InsnNode(ARETURN));
				break;
			}
		}
		return classNode;
	}

	private ClassNode transformActiveRenderInfo(ClassNode classNode, boolean obf) {
		String owner = obf ? "baj" : "net/minecraft/client/renderer/ActiveRenderInfo";
		String viewport = obf ? "i" : "viewport";
		String modelview = obf ? "j" : "modelview";
		String projection = obf ? "k" : "projection";
		String objectCoords = obf ? "l" : "objectCoords";
		String updateRenderInfo = obf ? "a" : "updateRenderInfo";
		String updateRenderInfoDesc = obf ? "(Lyz;Z)V" : "(Lnet/minecraft/entity/player/EntityPlayer;Z)V";
		final String fbDesc = "Ljava/nio/FloatBuffer;";
		final String ibDesc = "Ljava/nio/IntBuffer;";
		for (MethodNode method : classNode.methods) {
			if (updateRenderInfo.equals(method.name) && updateRenderInfoDesc.equals(method.desc)) {
				InsnList instructions = method.instructions;
				for (int i = 0; i < instructions.size(); i++) {
					AbstractInsnNode insnNode = instructions.get(i);
					if (insnNode.getOpcode() == ILOAD) {
						Iterator<AbstractInsnNode> iter = instructions.iterator(i);
						while (iter.hasNext()) {
							insnNode = iter.next();
							if (insnNode.getOpcode() == RETURN) {
								InsnList invocation = new InsnList();
								invocation.add(new VarInsnNode(FLOAD, 2));
								invocation.add(new VarInsnNode(FLOAD, 3));
								invocation.add(new FieldInsnNode(GETSTATIC, owner, modelview, fbDesc));
								invocation.add(new FieldInsnNode(GETSTATIC, owner, projection, fbDesc));
								invocation.add(new FieldInsnNode(GETSTATIC, owner, viewport, ibDesc));
								invocation.add(new FieldInsnNode(GETSTATIC, owner, objectCoords, fbDesc));
								invocation.add(new MethodInsnNode(INVOKESTATIC, PERSPECTIVE, "updateRenderInfo", "(FF" + fbDesc + fbDesc + ibDesc + fbDesc + ")V", false));
								instructions.insertBefore(insnNode, invocation);
								break;
							} else {
								iter.remove();
							}
						}
						break;
					}
				}
			}
		}
		return classNode;
	}

	private ClassNode transformEntityRenderer(ClassNode classNode, boolean obf) {
		String orientCamera = obf ? "h" : "orientCamera";
		String activeRenderInfo = obf ? "baj" : "net/minecraft/client/renderer/ActiveRenderInfo";
		String[] objectXYZ = { obf ? "a" : "objectX", obf ? "b" : "objectY", obf ? "c" : "objectZ" };
		String getMouseOver = obf ? "a" : "getMouseOver";
		String entityDesc = obf ? "Lsa;" : "Lnet/minecraft/entity/Entity;";
		String getMouseOverBLDesc = "(F)" + entityDesc;
		String entityRenderer = obf ? "blt" : "net/minecraft/client/renderer/EntityRenderer";
		String pointedEntity = obf ? "x" : "pointedEntity";
		boolean needsOrientCamera = true;
		boolean needsRenderWorld = true;
		boolean needsGetMouseOver = true;
		for (MethodNode method : classNode.methods) {
			if (needsOrientCamera && orientCamera.equals(method.name) && "(F)V".equals(method.desc)) {
				method.localVariables.clear();
				ListIterator<AbstractInsnNode> insns = method.instructions.iterator();
				while (insns.hasNext()) {
					AbstractInsnNode insnNode = insns.next();
					if (insnNode.getOpcode() == INVOKEVIRTUAL && "(DDDF)Z".equals(((MethodInsnNode) insnNode).desc)) {
						InsnList invocation = new InsnList();
						invocation.add(new VarInsnNode(ALOAD, 0));
						invocation.add(new VarInsnNode(FLOAD, 1));
						invocation.add(new MethodInsnNode(INVOKESTATIC, PERSPECTIVE, "orient", "(F)Z", false));
						method.instructions.insertBefore(insnNode, invocation);
						insns.remove();
						break;
					}
					insns.remove();
				}
				needsOrientCamera = false;
			}
			if (needsRenderWorld && "(FJ)V".equals(method.desc)) {
				for (int i = 0; i < method.instructions.size(); i++) {
					AbstractInsnNode insnNode = method.instructions.get(i);
					if (insnNode.getOpcode() == INVOKESTATIC && activeRenderInfo.equals(((MethodInsnNode) insnNode).owner)) {
						InsnList addCameraPosToViewerPos = new InsnList();
						for (int n = 0, s = 7; n < objectXYZ.length; n++, s += 2) {
							addCameraPosToViewerPos.add(new VarInsnNode(DLOAD, s));
							addCameraPosToViewerPos.add(new FieldInsnNode(GETSTATIC, activeRenderInfo, objectXYZ[n], "F"));
							addCameraPosToViewerPos.add(new InsnNode(F2D));
							addCameraPosToViewerPos.add(new InsnNode(DADD));
							addCameraPosToViewerPos.add(new VarInsnNode(DSTORE, s));
						}
						method.instructions.insert(insnNode, addCameraPosToViewerPos);
						break;
					}
				}
				needsRenderWorld = false;
			}
			if (needsGetMouseOver && getMouseOver.equals(method.name) && "(F)V".equals(method.desc)) {
				method.localVariables.clear();
				method.instructions.clear();
				method.instructions.add(new VarInsnNode(ALOAD, 0));
				method.instructions.add(new VarInsnNode(FLOAD, 1));
				method.instructions.add(new MethodInsnNode(INVOKESTATIC, BL_FORGE_HOOKS_CLIENT, "getMouseOver", getMouseOverBLDesc, false));
				method.instructions.add(new FieldInsnNode(PUTFIELD, entityRenderer, pointedEntity, entityDesc));
				method.instructions.add(new InsnNode(RETURN));
				needsGetMouseOver = false;
			}
			if (!needsOrientCamera && !needsRenderWorld && !needsGetMouseOver) {
				break;
			}
		}
		return classNode;
	}

	private ClassNode transformRenderManager(ClassNode classNode, boolean obf) {
		String cacheActiveRenderInfo = obf ? "a" : "cacheActiveRenderInfo";
		String cacheActiveRenderInfoDesc = obf ? "(Lahb;Lbqf;Lbbu;Lsv;Lsa;Lbbj;F)V" : "(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/settings/GameSettings;F)V";
		String fontRenderer = obf ? "r" : "fontRenderer";
		for (MethodNode method : classNode.methods) {
			if (cacheActiveRenderInfo.equals(method.name) && cacheActiveRenderInfoDesc.equals(method.desc)) {
				for (int i = method.instructions.size() - 1; i >= 0; i--) {
					AbstractInsnNode insnNode = method.instructions.get(i);
					if (insnNode.getOpcode() == RETURN) {
						method.instructions.insertBefore(insnNode, new MethodInsnNode(INVOKESTATIC, PERSPECTIVE, "cacheActiveRenderInfo", "()V", false));
						break;
					}
				}
				break;
			}
		}
		return classNode;
	}

	private ClassNode transformEntity(ClassNode classNode, boolean obf) {
		String setAngles = obf ? "c" : "setAngles";
		String entity = obf ? "sa" : "net/minecraft/entity/Entity";
		for (MethodNode method : classNode.methods) {
			if (setAngles.equals(method.name) && "(FF)V".equals(method.desc)) {
				method.instructions.clear();
				method.localVariables.clear();
				method.instructions.add(new VarInsnNode(ALOAD, 0));
				method.instructions.add(new VarInsnNode(FLOAD, 1));
				method.instructions.add(new VarInsnNode(FLOAD, 2));
				method.instructions.add(new MethodInsnNode(INVOKESTATIC, PERSPECTIVE, "setAngles", String.format("(L%s;FF)V", entity), false));
				method.instructions.add(new InsnNode(RETURN));
				break;
			}
		}
		return classNode;
	}
}
